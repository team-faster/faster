package com.faster.hub.app.hub.application.dto;

import com.faster.hub.app.hub.domain.entity.Hub;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateHubApplicationResponseDto(
    UUID id,
    String name,
    String address,
    String latitude,
    String longitude,
    Long createBy,
    LocalDateTime createAt
) {

  public static UpdateHubApplicationResponseDto from(Hub hub) {
    return UpdateHubApplicationResponseDto.builder()
        .id(hub.getId())
        .name(hub.getName())
        .address(hub.getAddress())
        .latitude(hub.getLatitude())
        .longitude(hub.getLongitude())
        .createBy(hub.getCreatedBy())
        .createAt(hub.getCreatedAt())
        .build();
  }
}
