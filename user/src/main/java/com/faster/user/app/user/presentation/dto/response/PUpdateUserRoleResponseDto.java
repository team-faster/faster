package com.faster.user.app.user.presentation.dto.response;


import com.common.resolver.dto.UserRole;
import com.faster.user.app.user.application.dto.response.AUpdateUserRoleResponseDto;

public record PUpdateUserRoleResponseDto(
    Long id,
    UserRole role
) {
  public static PUpdateUserRoleResponseDto from(AUpdateUserRoleResponseDto responseDto) {
    return new PUpdateUserRoleResponseDto(responseDto.id(), responseDto.role());
  }
}
