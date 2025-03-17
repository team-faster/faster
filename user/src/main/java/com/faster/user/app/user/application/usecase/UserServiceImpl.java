package com.faster.user.app.user.application.usecase;

import com.common.exception.CustomException;
import com.common.resolver.dto.UserRole;
import com.common.response.PageResponse;
import com.faster.user.app.global.exception.enums.UserErrorCode;
import com.faster.user.app.user.application.dto.UpdateUserRoleRequestDto;
import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.infrastructure.persistence.jpa.UserJpaRepository;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserQuerydslResponseDto;
import com.faster.user.app.user.presentation.dto.UpdateUserRoleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserJpaRepository userJpaRepository;

  private User getUserByUserId(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND_BY_ID));
  }

  @Transactional
  @Override
  public UpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, UpdateUserRoleRequestDto requestDto) {
    User userByUserId = getUserByUserId(userId);

    userByUserId.updateUserRole(requestDto.role());
    User user = userJpaRepository.save(userByUserId);

    return UpdateUserRoleResponseDto.from(user);
  }

  @Override
  public Page<QUserQuerydslResponseDto> getAllUsers(String username, String name, String slackId, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    return userJpaRepository.searchUsers(username, name, slackId, pageable);
  }

}
