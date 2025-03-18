package com.faster.user.app.user.application.dto.request;

import com.common.resolver.dto.UserRole;

public record AUpdateUserRoleRequestDto(
    UserRole role,
    Long updatedBy
) {
}
