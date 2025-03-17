package com.faster.user.app.auth.application.usecase;


import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGNUP_INVALID_SLACK_ID_FORMAT;
import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGNUP_INVALID_USERNAME_FORMAT;
import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGN_IN_INVALID_USERNAME;

import com.common.exception.CustomException;
import com.faster.user.app.auth.application.dto.CreateUserRequestDto;
import com.faster.user.app.auth.application.dto.SignInUserRequestDto;
import com.faster.user.app.auth.jwt.JwtProvider;
import com.faster.user.app.auth.presentation.dto.CreateUserResponseDto;
import com.faster.user.app.auth.presentation.dto.SignInUserResponseDto;
import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.infrastructure.persistence.jpa.UserRepositoryAdapter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepositoryAdapter userRepositoryAdapter;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  private void findUserByUsernameOrSlackId(String username, String slackId) {
    Optional<User> existUser = userRepositoryAdapter.findUserByUsernameOrSlackId(username, slackId);

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
    return userRepositoryAdapter.findUserByUsername(username)
        .orElseThrow(() -> new CustomException(SIGN_IN_INVALID_USERNAME));
  }

  @Transactional
  @Override
  public CreateUserResponseDto createUser(CreateUserRequestDto requestDto) {
    findUserByUsernameOrSlackId(requestDto.username(), requestDto.slackId());
    String encodedPassword = passwordEncoder.encode(requestDto.password());
    User newUser = User.of(
        requestDto.username(),
        encodedPassword,
        requestDto.name(),
        requestDto.slackId()
    );

    User savedUser = userRepositoryAdapter.save(newUser);

    return CreateUserResponseDto.from(savedUser);
  }

  @Transactional
  @Override
  public SignInUserResponseDto signInUser(SignInUserRequestDto requestDto) {
    User user = getUserByUsername(requestDto.username());

    if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
      throw new CustomException(SIGN_IN_INVALID_USERNAME);
    }

    String accessToken = jwtProvider.createAccessToken(requestDto.username());
    String refreshToken = jwtProvider.createRefreshToken(requestDto.username());

    return SignInUserResponseDto.of(accessToken, refreshToken, user.getId(), user.getRole().name());
  }

}