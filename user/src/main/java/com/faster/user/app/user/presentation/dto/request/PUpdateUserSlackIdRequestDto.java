package com.faster.user.app.user.presentation.dto.request;

import com.faster.user.app.user.application.dto.request.AUpdateUserSlackIdRequestDto;

public record PUpdateUserSlackIdRequestDto(
    String newSlackId,
    Long updatedBy
) {
  public static AUpdateUserSlackIdRequestDto from(PUpdateUserSlackIdRequestDto requestDto) {
    return new AUpdateUserSlackIdRequestDto(
        requestDto.newSlackId(),
        requestDto.updatedBy()
    );
  }
}
