package com.common.resolver.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
  ROLE_MASTER("마스터 관리자"),
  ROLE_HUB("허브 관리자"),
  ROLE_COMPANY("업체 담당자"),
  ROLE_DELIVERY("배송 담당자");

  private final String description;
}
