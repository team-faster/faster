package com.faster.user.app.auth.application.usecase;


import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGNUP_INVALID_SLACK_ID_FORMAT;
import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGNUP_INVALID_USERNAME_FORMAT;
import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGN_IN_INVALID_PASSWORD;
import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGN_IN_INVALID_USERNAME;

import com.common.exception.CustomException;
import com.common.resolver.dto.UserRole;
import com.faster.user.app.auth.application.dto.SaveUserRequestDto;
import com.faster.user.app.auth.application.dto.SignInUserRequestDto;
import com.faster.user.app.auth.jwt.JwtProvider;
import com.faster.user.app.auth.presentation.dto.response.SaveUserResponseDto;
import com.faster.user.app.auth.presentation.dto.response.SignInUserResponseDto;
import com.faster.user.app.global.exception.enums.AuthErrorCode;
import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.domain.repository.UserRepository;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  @Value("${jwt.refresh.expiration}")
  private Long refreshTokenExpiration;

  private void findUserByUsernameOrSlackId(String username, String slackId) {
    Optional<User> existUser = userRepository.findUserByUsernameOrSlackId(username, slackId);

    if (existUser.isPresent()) {
      User user = existUser.get();
      if (user.getUsername().equals(username)) {
        throw new CustomException(SIGNUP_INVALID_USERNAME_FORMAT);
      }
      if (user.getSlackId().equals(slackId)) {
        throw new CustomException(SIGNUP_INVALID_SLACK_ID_FORMAT);
      }
    }
  }

  private User getUserByUsername(String username) {
    return userRepository.findUserByUsername(username)
        .orElseThrow(() -> new CustomException(SIGN_IN_INVALID_USERNAME));
  }

  @Transactional
  @Override
  public SaveUserResponseDto createUser(SaveUserRequestDto requestDto) {
    findUserByUsernameOrSlackId(requestDto.username(), requestDto.slackId());
    String encodedPassword = passwordEncoder.encode(requestDto.password());
    User newUser = User.of(
        requestDto.username(),
        encodedPassword,
        requestDto.name(),
        requestDto.slackId()
    );

    User savedUser = userRepository.save(newUser);

    return SaveUserResponseDto.from(savedUser);
  }

  @Transactional
  @Override
  public SignInUserResponseDto signInUser(SignInUserRequestDto requestDto) {
    User user = getUserByUsername(requestDto.username());

    if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
      throw new CustomException(SIGN_IN_INVALID_PASSWORD);
    }

    String accessToken = jwtProvider.createAccessToken(user.getId(), user.getRole());
    String refreshToken = jwtProvider.createRefreshToken(user.getId());

    storeRefreshToken(user.getId(), refreshToken);

    return SignInUserResponseDto.of(accessToken, refreshToken, user.getId(), user.getRole().name());
  }

  @Override
  public void logout(Long userId) {
    deleteRefreshToken(userId);
  }

  @Override
  public String generateNewAccessToken(Long userId) {
    String refreshToken = getRefreshToken(userId);
    if (refreshToken == null) {
      throw new CustomException(AuthErrorCode.REFRESH_TOKEN_EXPIRED);
    }
    if (!jwtProvider.validateToken(refreshToken)) {
      throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }

    Long userIdFromToken = jwtProvider.getUserIdFromToken(refreshToken);
    UserRole userRoleFromToken = jwtProvider.getUserRoleFromToken(refreshToken);

    return jwtProvider.createAccessToken(userIdFromToken, userRoleFromToken);
  }

  private String getRefreshToken(Long userId) {
    String key = "refreshToken:" + userId;
    Object token = redisTemplate.opsForValue().get(key);
    return token != null ? token.toString() : null;
  }

  private void deleteRefreshToken(Long userId) {
    String key = "refreshToken:" + userId;
    redisTemplate.delete(key);
  }

  private void storeRefreshToken(Long userId, String refreshToken) {
    String key = "refreshToken:" + userId;
    redisTemplate.opsForValue().set(key, refreshToken, refreshTokenExpiration, TimeUnit.SECONDS);
  }
}

