package com.faster.user.app.user.presentation.dto.request;

import com.common.resolver.dto.UserRole;
import com.faster.user.app.user.application.dto.request.AUpdateUserRoleRequestDto;

public record PUpdateUserRoleRequestDto(
    UserRole role,
    Long updatedBy
) {

  public static AUpdateUserRoleRequestDto from(PUpdateUserRoleRequestDto requestDto) {
    return new AUpdateUserRoleRequestDto(
        requestDto.role,
        requestDto.updatedBy
    );
  }
}
