package com.faster.company.app.company.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyType {
  SUPPLIER("생산 업체"),
  RECEIVER("수령 업체");

  private final String description;
}
