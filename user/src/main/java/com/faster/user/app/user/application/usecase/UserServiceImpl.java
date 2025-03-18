package com.faster.user.app.user.application.usecase;

import com.common.exception.CustomException;
import com.common.response.PageResponse;
import com.faster.user.app.user.presentation.dto.DeleteUserResponseDto;
import com.faster.user.app.global.exception.enums.UserErrorCode;
import com.faster.user.app.user.application.dto.DeleteUserRequestDto;
import com.faster.user.app.user.application.dto.UpdateUserRoleRequestDto;
import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.domain.repository.UserRepository;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserProjection;
import com.faster.user.app.user.presentation.dto.GetAllUserResponseDto;
import com.faster.user.app.user.presentation.dto.UpdateUserRoleResponseDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private User getUserByUserId(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND_BY_ID));
  }

  @Transactional
  @Override
  public UpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, UpdateUserRoleRequestDto requestDto) {
    User userByUserId = getUserByUserId(userId);

    userByUserId.updateUserRole(requestDto.role());
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
    user.softDeleteUser(requestDto.deleterId(), LocalDateTime.now());

    return new DeleteUserResponseDto(user.getId());
  }
}
