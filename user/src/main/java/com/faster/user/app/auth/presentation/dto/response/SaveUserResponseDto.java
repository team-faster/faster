package com.faster.user.app.auth.presentation.dto.response;

import com.faster.user.app.user.domain.entity.User;

public record SaveUserResponseDto(
    String username,
    String name,
    String slackId
) {
  public static SaveUserResponseDto from(User savedUser) {
    return new SaveUserResponseDto(
        savedUser.getUsername(),
        savedUser.getName(),
        savedUser.getSlackId()
    );
  }
}
