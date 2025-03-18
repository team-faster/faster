package com.faster.user.app.user.presentation.dto;

import com.faster.user.app.user.domain.entity.User;

public record GetUserSlackIdResponseDto(
    String slackId
) {

  public static GetUserSlackIdResponseDto from(User user) {
    return new GetUserSlackIdResponseDto(user.getSlackId());
  }
}
