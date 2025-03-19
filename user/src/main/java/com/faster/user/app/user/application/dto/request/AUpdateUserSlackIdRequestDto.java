package com.faster.user.app.user.application.dto.request;

import com.faster.user.app.user.presentation.dto.request.PUpdateUserSlackIdRequestDto;

public record AUpdateUserSlackIdRequestDto(
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
