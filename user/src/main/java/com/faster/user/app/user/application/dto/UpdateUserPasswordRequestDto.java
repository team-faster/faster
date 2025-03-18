package com.faster.user.app.user.application.dto;

public record UpdateUserPasswordRequestDto(
    String currentPassword,
    String newPassword,
    Long updatedBy
) {
}
