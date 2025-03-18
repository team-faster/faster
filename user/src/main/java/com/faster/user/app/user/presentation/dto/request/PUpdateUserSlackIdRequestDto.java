package com.faster.user.app.user.presentation.dto.request;

public record PUpdateUserSlackIdRequestDto(
    String newSlackId,
    Long updatedBy
) {
}
