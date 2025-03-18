package com.faster.user.app.user.presentation.dto.request;

public record PUpdateUserPasswordRequestDto(
    String currentPassword,
    String newPassword,
    Long updatedBy
) {
}
