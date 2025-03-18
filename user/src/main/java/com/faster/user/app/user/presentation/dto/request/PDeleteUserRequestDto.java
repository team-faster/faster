package com.faster.user.app.user.presentation.dto.request;


import com.faster.user.app.user.application.dto.request.ADeleteUserRequestDto;

public record PDeleteUserRequestDto(
    Long deleterId) {

  public static ADeleteUserRequestDto from(PDeleteUserRequestDto requestDto) {
    return new ADeleteUserRequestDto(requestDto.deleterId());
  }
}
