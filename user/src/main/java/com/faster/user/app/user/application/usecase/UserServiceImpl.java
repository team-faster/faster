package com.faster.user.app.user.application.usecase;

import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGN_IN_INVALID_PASSWORD;
import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGN_IN_INVALID_USERNAME;

import com.common.exception.CustomException;
import com.common.response.PageResponse;
import com.faster.user.app.user.application.dto.UpdateUserPasswordRequestDto;
import com.faster.user.app.user.application.dto.UpdateUserSlackIdRequestDto;
import com.faster.user.app.user.presentation.dto.DeleteUserResponseDto;
import com.faster.user.app.global.exception.enums.UserErrorCode;
import com.faster.user.app.user.application.dto.DeleteUserRequestDto;
import com.faster.user.app.user.application.dto.UpdateUserRoleRequestDto;
import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.domain.repository.UserRepository;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserProjection;
import com.faster.user.app.user.presentation.dto.GetAllUserResponseDto;
import com.faster.user.app.user.presentation.dto.GetUserResponseDto;
import com.faster.user.app.user.presentation.dto.GetUserSlackIdResponseDto;
import com.faster.user.app.user.presentation.dto.UpdateUserRoleResponseDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private User getUserByUserId(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND_BY_ID));
  }

  @Transactional
  @Override
  public UpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, UpdateUserRoleRequestDto requestDto) {
    User userByUserId = getUserByUserId(userId);

    userByUserId.updateUserRole(requestDto.role());
    userByUserId.createdBy(requestDto.updatedBy());
    User user = userRepository.save(userByUserId);

    return UpdateUserRoleResponseDto.from(user);
  }

  @Override
  public PageResponse<GetAllUserResponseDto> getAllUsers(String username, String name, String slackId, Integer page,
                                                         Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<QUserProjection> pageResult = userRepository.searchUsers(username, name, slackId, pageable);

    return PageResponse.from(pageResult)
        .map(user -> new GetAllUserResponseDto(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getSlackId(),
            user.getRole(),
            user.getCreatedAt(),
            user.getCreatedBy(),
            user.getUpdatedAt(),
            user.getUpdatedBy(),
            user.getDeletedAt(),
            user.getDeletedBy()
        ));
  }

  @Override
  public DeleteUserResponseDto deleteUserByUserId(Long userId, DeleteUserRequestDto requestDto) {
    User user = getUserByUserId(userId);
    user.delete(LocalDateTime.now(), requestDto.deleterId());

    return DeleteUserResponseDto.from(user.getId());
  }

  @Override
  public void updateUserPassword(Long userId, UpdateUserPasswordRequestDto requestDto) {
    User user = getUserByUserId(userId);

    if (!passwordEncoder.matches(requestDto.currentPassword(), user.getPassword())) {
      throw new CustomException(SIGN_IN_INVALID_PASSWORD);
    }

    String encodedPassword = passwordEncoder.encode(requestDto.newPassword());
    user.updateUserPassword(encodedPassword);

    userRepository.save(user);
  }

  @Override
  public void updateUserSlackId(Long userId, UpdateUserSlackIdRequestDto requestDto) {
    User user = getUserByUserId(userId);
    user.updateUserSlackId(requestDto.newSlackId());

    userRepository.save(user);
  }

  @Override
  public GetUserResponseDto getUser(Long userId) {
    User user = getUserByUserId(userId);

    return GetUserResponseDto.from(user);
  }

  @Override
  public GetUserSlackIdResponseDto getInternalUserSlackIdByUserId(Long userId) {
    User user = getUserByUserId(userId);

    return GetUserSlackIdResponseDto.from(user);
  }
}
