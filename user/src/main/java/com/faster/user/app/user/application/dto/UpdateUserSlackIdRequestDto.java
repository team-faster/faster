package com.faster.user.app.user.application.dto;

public record UpdateUserSlackIdRequestDto(
    String newSlackId,
    Long updatedBy
) {
}
