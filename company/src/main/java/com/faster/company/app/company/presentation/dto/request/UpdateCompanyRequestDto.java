package com.faster.company.app.company.presentation.dto.request;

import com.faster.company.app.company.application.dto.request.UpdateCompanyApplicationRequestDto;
import java.util.UUID;

public record UpdateCompanyRequestDto(
    UUID companyId,
    UUID hubId,
    String name,
    String contact,
    String address
) {

  public UpdateCompanyApplicationRequestDto toApplicationDto() {
    return UpdateCompanyApplicationRequestDto.builder()
        .companyId(companyId)
        .hubId(hubId)
        .name(name)
        .contact(contact)
        .address(address)
        .build();
  }
}
