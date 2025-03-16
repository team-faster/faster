package com.faster.message.app.message.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

  HUB_MANAGER("HUB_MANAGER"),
  COMPANY_DELIVERY_MANAGER("COMPANY_DELIVERY_MANAGER");

  private final String type;
}
