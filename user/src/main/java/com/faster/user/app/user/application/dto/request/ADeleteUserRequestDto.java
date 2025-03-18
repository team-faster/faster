package com.faster.user.app.user.application.dto.request;


import com.faster.user.app.user.presentation.dto.request.PDeleteUserRequestDto;

public record ADeleteUserRequestDto(
    Long deleterId) {

  public static PDeleteUserRequestDto from(ADeleteUserRequestDto requestDto) {
    return new PDeleteUserRequestDto(requestDto.deleterId());
  }
}
