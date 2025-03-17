package com.faster.user.app.user.application.dto;

import java.time.LocalDateTime;


public record DeleteUserRequestDto(
    Long deleterId) {

  public static DeleteUserRequestDto of(DeleteUserRequestDto requestDto) {
    return new DeleteUserRequestDto(requestDto.deleterId());
  }
}
