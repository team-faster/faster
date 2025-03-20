package com.faster.company.app.company.presentation.dto.response;

import com.faster.company.app.company.application.dto.response.GetCompaniesApplicationResponseDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetCompaniesResponseDto (
    UUID id,
    UUID hubId,
    Long managerId,
    String name,
    String contact,
    String address,
    String type,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public static GetCompaniesResponseDto from(GetCompaniesApplicationResponseDto projection) {
    return GetCompaniesResponseDto.builder()
        .id(projection.id())
        .hubId(projection.hubId())
        .managerId(projection.managerId())
        .name(projection.name())
        .contact(projection.contact())
        .address(projection.address())
        .type(projection.type())
        .createdAt(projection.createdAt())
        .updatedAt(projection.updatedAt())
        .build();
  }
}
