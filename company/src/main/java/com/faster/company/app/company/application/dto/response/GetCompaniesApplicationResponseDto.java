package com.faster.company.app.company.application.dto.response;

import com.faster.company.app.company.domain.projection.SearchCompaniesProjection;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetCompaniesApplicationResponseDto(
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

  public static GetCompaniesApplicationResponseDto from(
      SearchCompaniesProjection projection) {
    return GetCompaniesApplicationResponseDto.builder()
        .id(projection.id())
        .hubId(projection.hubId())
        .managerId(projection.managerId())
        .name(projection.name())
        .contact(projection.contact())
        .address(projection.address())
        .type(projection.type().name())
        .createdAt(projection.createdAt())
        .updatedAt(projection.updatedAt())
        .build();
  }
}
