package com.faster.delivery.app.delivery.infrastructure.client.dto.company;

import com.faster.delivery.app.delivery.application.dto.CompanyDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CompanyGetResponseDto (
    UUID id,
    Long companyManagerId,
    String companyManagerName,
    String companyManagerSlackId,
    String name,
    String contact,
    String address,
    UUID hubId,
    String type
) {

  public CompanyDto toCompanyDto() {
    return CompanyDto.builder()
        .id(id)
        .companyManagerId(companyManagerId)
        .companyManagerName(companyManagerName)
        .companyManagerSlackId(companyManagerSlackId)
        .name(name)
        .contact(contact)
        .address(address)
        .hubId(hubId)
        .type(type)
        .build();
  }
}
