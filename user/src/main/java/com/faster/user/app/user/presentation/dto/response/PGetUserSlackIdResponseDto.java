package com.faster.user.app.user.presentation.dto.response;

import com.faster.user.app.user.application.dto.response.AGetUserSlackIdResponseDto;

public record PGetUserSlackIdResponseDto(
    String slackId
) {

  public static PGetUserSlackIdResponseDto from(AGetUserSlackIdResponseDto responseDto) {
    return new PGetUserSlackIdResponseDto(responseDto.slackId());
  }
}
