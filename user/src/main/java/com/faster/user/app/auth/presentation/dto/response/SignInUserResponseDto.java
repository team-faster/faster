package com.faster.user.app.auth.presentation.dto.response;

public record SignInUserResponseDto(
    String accessToken,
    String refreshToken,
    Long userId,
    String role
) {
  public static SignInUserResponseDto of(String accessToken, String refreshToken, Long userId, String role) {
    return new SignInUserResponseDto(
        accessToken,
        refreshToken,
        userId,
        role
    );
  }
}
