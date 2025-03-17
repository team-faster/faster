package com.faster.message.app.message.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {

  HUB_MANAGER("허브 배송 담당자"),
  COMPANY_DELIVERY_MANAGER("업체 배송 담당자");

  private final String type;
}
