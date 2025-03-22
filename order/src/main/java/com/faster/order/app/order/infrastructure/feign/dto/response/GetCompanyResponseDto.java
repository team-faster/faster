package com.faster.order.app.order.infrastructure.feign.dto.response;

import com.faster.order.app.order.application.dto.response.GetCompanyApplicationResponseDto;
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
        .id(companyId)
        .hubId(hubId)
        .companyManagerId(companyManagerUserId)
        .name(name)
        .contact(contact)
        .address(address)
        .type(type)
        .build();
  }
}
