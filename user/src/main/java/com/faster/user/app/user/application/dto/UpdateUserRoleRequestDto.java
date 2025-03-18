package com.faster.user.app.user.application.dto;

import com.common.resolver.dto.UserRole;

public record UpdateUserRoleRequestDto(
    UserRole role,
    Long updatedBy
) {
}
