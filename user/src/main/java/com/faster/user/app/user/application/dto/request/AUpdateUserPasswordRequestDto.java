package com.faster.user.app.user.application.dto.request;

import com.faster.user.app.user.presentation.dto.request.PUpdateUserPasswordRequestDto;

public record AUpdateUserPasswordRequestDto(
    String currentPassword,
    String newPassword,
    Long updatedBy
) {
  public static AUpdateUserPasswordRequestDto from(PUpdateUserPasswordRequestDto requestDto) {
    return new AUpdateUserPasswordRequestDto(
        requestDto.currentPassword(),
        requestDto.newPassword(),
        requestDto.updatedBy()
    );
  }
}
