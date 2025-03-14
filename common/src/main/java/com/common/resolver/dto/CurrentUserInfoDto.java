package com.common.resolver.dto;

public record CurrentUserInfoDto(Long userId, String role) {

  public static CurrentUserInfoDto of(Long userId, String role) {
    return new CurrentUserInfoDto(userId, role);
  }
}
