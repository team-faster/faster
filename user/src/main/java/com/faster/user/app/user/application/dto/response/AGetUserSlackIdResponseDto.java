package com.faster.user.app.user.application.dto.response;

import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.presentation.dto.response.PGetUserSlackIdResponseDto;

public record AGetUserSlackIdResponseDto(
    String slackId
) {

  public static PGetUserSlackIdResponseDto from(AGetUserSlackIdResponseDto responseDto) {
    return new PGetUserSlackIdResponseDto(responseDto.slackId());
  }

  public static AGetUserSlackIdResponseDto from(User user) {
    return new AGetUserSlackIdResponseDto(user.getSlackId());
  }
}
