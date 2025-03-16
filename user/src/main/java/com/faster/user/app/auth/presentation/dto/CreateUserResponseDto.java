package com.faster.user.app.auth.presentation.dto;

import com.faster.user.app.user.domain.entity.User;

public record CreateUserResponseDto(
    String username,
    String name,
    String slackId
) {
  public static CreateUserResponseDto of(User savedUser) {
    return new CreateUserResponseDto(
        savedUser.getUsername(),
        savedUser.getName(),
        savedUser.getSlackId()
    );
  }
}
