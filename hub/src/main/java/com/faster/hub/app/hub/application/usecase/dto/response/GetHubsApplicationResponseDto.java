package com.faster.hub.app.hub.application.usecase.dto.response;

import com.faster.hub.app.hub.domain.projection.SearchHubProjection;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetHubsApplicationResponseDto(
    UUID id,
    Long managerId,
    String name,
    String address,
    String latitude,
    String longitude,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
){
  public static GetHubsApplicationResponseDto from(
      SearchHubProjection projection) {
    return GetHubsApplicationResponseDto.builder()
        .id(projection.id())
        .managerId(projection.managerId())
        .name(projection.name())
        .address(projection.address())
        .latitude(projection.latitude())
        .longitude(projection.longitude())
        .createdAt(projection.createdAt())
        .updatedAt(projection.updatedAt())
        .build();
  }
}