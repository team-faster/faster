package com.faster.user.app.auth.application.usecase;


import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGNUP_INVALID_SLACK_ID_FORMAT;
import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGNUP_INVALID_USERNAME_FORMAT;

import com.faster.user.app.auth.application.dto.CreateUserRequestDto;
import com.faster.user.app.auth.presentation.dto.CreateUserResponseDto;
import com.faster.user.app.global.exception.UserAlreadyExistsBySlackIdException;
import com.faster.user.app.global.exception.UserAlreadyExistsByUsernameException;
import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.domain.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService implements AuthUseCase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private void findUserByUsernameOrSlackId(String username, String slackId) {
    Optional<User> existUser = userRepository.findUserByUsernameOrSlackId(username, slackId);

    if (existUser.isPresent()) {
      User user = existUser.get();
      if (user.getUsername().equals(username)) {
        throw new UserAlreadyExistsByUsernameException(SIGNUP_INVALID_USERNAME_FORMAT.getMessage());
      }
      if (user.getSlackId().equals(slackId)) {
        throw new UserAlreadyExistsBySlackIdException(SIGNUP_INVALID_SLACK_ID_FORMAT.getMessage());
      }
    }
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

    User savedUser = userRepository.save(newUser);

    return CreateUserResponseDto.of(savedUser);
  }


}