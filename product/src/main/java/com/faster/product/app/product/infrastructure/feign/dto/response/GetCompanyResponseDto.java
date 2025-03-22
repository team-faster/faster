package com.faster.product.app.product.infrastructure.feign.dto.response;

import com.faster.product.app.product.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.GetCompanyApplicationResponseDto.CompanyType;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetCompanyResponseDto(
    UUID companyId,
    UUID hubId,
    Long companyManagerUserId,
    String name,
    String contact,
    String address,
    String type,
    String companyManagerSlackId,
    String companyManagerName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public GetCompanyApplicationResponseDto toApplicationDto() {
    return GetCompanyApplicationResponseDto.builder()
        .companyId(companyId)
        .hubId(hubId)
        .companyManagerUserId(companyManagerUserId)
        .name(name)
        .contact(contact)
        .address(address)
        .type(CompanyType.fromString(type))
        .companyManagerSlackId(companyManagerSlackId)
        .companyManagerName(companyManagerName)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();
  }
}
