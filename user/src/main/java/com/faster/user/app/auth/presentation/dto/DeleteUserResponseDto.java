package com.faster.user.app.auth.presentation.dto;

public record DeleteUserResponseDto(
    Long deletedId
) {

  public static DeleteUserResponseDto of(Long deletedId) {
    return new DeleteUserResponseDto(deletedId);
  }
}
