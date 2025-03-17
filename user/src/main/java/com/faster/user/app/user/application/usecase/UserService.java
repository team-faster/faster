package com.faster.user.app.user.application.usecase;


import com.common.resolver.dto.UserRole;
import com.common.response.PageResponse;
import com.faster.user.app.user.application.dto.UpdateUserRoleRequestDto;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserQuerydslResponseDto;
import com.faster.user.app.user.presentation.dto.UpdateUserRoleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  UpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, UpdateUserRoleRequestDto requestDto);

  Page<QUserQuerydslResponseDto> getAllUsers(String username, String name, String slackId, Integer page, Integer size);
}
