package com.faster.user.app.user.presentation.dto.response;

import com.faster.user.app.user.application.dto.response.ADeleteUserResponseDto;

public record PDeleteUserResponseDto(
    Long deletedId
) {
  public static PDeleteUserResponseDto from(ADeleteUserResponseDto requestDto) {
    return new PDeleteUserResponseDto(requestDto.deletedId());
  }
}
