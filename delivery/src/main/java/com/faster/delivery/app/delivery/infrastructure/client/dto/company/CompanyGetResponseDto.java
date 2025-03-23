package com.faster.delivery.app.delivery.infrastructure.client.dto.company;

import com.faster.delivery.app.delivery.application.dto.CompanyDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CompanyGetResponseDto (
    UUID companyId,
    Long companyManagerUserId,
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
        .companyId(this.companyId)
        .companyManagerUserId(this.companyManagerUserId)
        .companyManagerName(this.companyManagerName)
        .companyManagerSlackId(this.companyManagerSlackId)
        .name(this.name)
        .contact(this.contact)
        .address(this.address)
        .hubId(this.hubId)
        .type(this.type)
        .build();
  }
}
