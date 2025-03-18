package com.faster.user.app.user.application.dto.response;


import com.common.resolver.dto.UserRole;
import com.faster.user.app.user.domain.entity.User;

public record AUpdateUserRoleResponseDto(
    Long id,
    UserRole role
) {
  public static AUpdateUserRoleResponseDto from(User user) {
    return new AUpdateUserRoleResponseDto(user.getId(), user.getRole());
  }
}
