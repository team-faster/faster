package com.faster.user.app.user.application.dto.response;

public record ADeleteUserResponseDto(
    Long deletedId
) {
  public static ADeleteUserResponseDto from(Long deletedId) {
    return new ADeleteUserResponseDto(deletedId);
  }
}
