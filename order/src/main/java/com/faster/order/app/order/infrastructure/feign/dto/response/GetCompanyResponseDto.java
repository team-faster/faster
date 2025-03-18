package com.faster.order.app.order.infrastructure.feign.dto.response;

import com.faster.order.app.order.application.dto.response.GetCompanyApplicationResponseDto;
import java.util.UUID;

public record GetCompanyResponseDto(
   UUID id,
   UUID hubId,
   UUID companyManagerId,
   String name,
   String contact,
   String address,
   String type
) {

  public GetCompanyApplicationResponseDto toApplicationDto() {
    return GetCompanyApplicationResponseDto.builder()
        .id(id)
        .hubId(hubId)
        .companyManagerId(companyManagerId)
        .name(name)
        .contact(contact)
        .address(address)
        .type(type)
        .build();
  }
}
