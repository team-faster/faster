package com.faster.user.app.user.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_HUB("ROLE_HUB"),
  ROLE_DELIVERY("ROLE_DELIVERY"),
  ROLE_COMPANY("ROLE_COMPANY"),;

  private final String description;
}