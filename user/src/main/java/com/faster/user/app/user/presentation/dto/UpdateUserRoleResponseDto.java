package com.faster.user.app.user.presentation.dto;


import com.common.resolver.dto.UserRole;
import com.faster.user.app.user.domain.entity.User;

public record UpdateUserRoleResponseDto(
    Long id,
    UserRole role
) {
  public static UpdateUserRoleResponseDto from(User user) {
    return new UpdateUserRoleResponseDto(user.getId(), user.getRole());
  }
}
