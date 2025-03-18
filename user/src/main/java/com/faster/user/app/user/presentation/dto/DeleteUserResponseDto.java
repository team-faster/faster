package com.faster.user.app.user.presentation.dto;

import com.faster.user.app.user.domain.entity.User;

public record DeleteUserResponseDto(
    Long deletedId
) {
  public static DeleteUserResponseDto from(Long deletedId) {
    return new DeleteUserResponseDto(deletedId);
  }
}
