package com.faster.user.app.auth.presentation.dto;

public record CreateUserResponseDto(
    String username,
    String name,
    String slackId
) {
}
