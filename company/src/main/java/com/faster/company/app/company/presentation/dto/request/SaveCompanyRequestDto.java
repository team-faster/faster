package com.faster.company.app.company.presentation.dto.request;

import com.faster.company.app.company.application.dto.request.SaveCompanyApplicationRequestDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SaveCompanyRequestDto(
    UUID hubId,
    Long companyManagerId,
    String name,
    String contact,
    String address,
    CompanyTypeDto type
) {

  public SaveCompanyApplicationRequestDto toApplicationDto() {

    return SaveCompanyApplicationRequestDto.builder()
        .hubId(hubId)
        .companyManagerId(companyManagerId)
        .name(name)
        .contact(contact)
        .address(address)
        .type(type.toString())
        .build();
  }

  enum CompanyTypeDto {
    SUPPLIER, RECEIVER
  }
}
