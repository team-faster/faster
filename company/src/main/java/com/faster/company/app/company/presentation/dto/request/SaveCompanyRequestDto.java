package com.faster.company.app.company.presentation.dto.request;

import com.faster.company.app.company.domain.enums.CompanyType;
import lombok.Builder;

@Builder
public record SaveCompanyRequestDto(
    String hubId,
    String companyManagerId,
    String name,
    String address,
    CompanyType type
) {

  public static SaveCompanyRequestDto of(String hubId, String companyManagerId,
      String name, String address, CompanyType type) {

    return SaveCompanyRequestDto.builder()
        .hubId(hubId)
        .companyManagerId(companyManagerId)
        .name(name)
        .address(address)
        .type(type)
        .build();
  }
}
