package com.faster.user.app.user.presentation.dto.request;

import com.faster.user.app.user.application.dto.request.AUpdateUserPasswordRequestDto;

public record PUpdateUserPasswordRequestDto(
    String currentPassword,
    String newPassword,
    Long updatedBy
) {

  public static AUpdateUserPasswordRequestDto fromDto(PUpdateUserPasswordRequestDto requestDto) {
    return new AUpdateUserPasswordRequestDto(
        requestDto.currentPassword(),
        requestDto.newPassword(),
        requestDto.updatedBy()
    );
  }
}
