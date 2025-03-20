package com.faster.hub.app.hub.presentation.dto.response;

import com.faster.hub.app.hub.application.usecase.dto.response.GetHubsApplicationResponseDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetHubsResponseDto (
    UUID id,
    Long managerId,
    String name,
    String address,
    String latitude,
    String longitude,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
){
  public static GetHubsResponseDto from(GetHubsApplicationResponseDto dto) {
    return GetHubsResponseDto.builder()
        .id(dto.id())
        .managerId(dto.managerId())
        .name(dto.name())
        .address(dto.address())
        .latitude(dto.latitude())
        .longitude(dto.longitude())
        .createdAt(dto.createdAt())
        .updatedAt(dto.updatedAt())
        .build();
  }
}
