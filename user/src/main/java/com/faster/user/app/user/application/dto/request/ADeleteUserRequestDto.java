package com.faster.user.app.user.application.dto.request;


import com.faster.user.app.user.presentation.dto.request.PDeleteUserRequestDto;

public record ADeleteUserRequestDto(
    Long deleterId) {

  public static ADeleteUserRequestDto from(PDeleteUserRequestDto requestDto) {
    return new ADeleteUserRequestDto(requestDto.deleterId());
  }
}
