package com.faster.product.app.product.infrastructure.feign.dto.response;

import com.faster.product.app.product.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.GetCompanyApplicationResponseDto.Type;
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
        .type(Type.fromString(type))
        .build();
  }
}
