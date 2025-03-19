package com.faster.hub.app.hub.application.usecase.dto.response;

import com.faster.hub.app.hub.domain.entity.Hub;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SaveHubApplicationResponseDto(
    UUID id,
    String name,
    String address,
    String latitude,
    String longitude,
    Long createBy,
    LocalDateTime createAt
) {

  public static SaveHubApplicationResponseDto from(Hub hub) {
    return SaveHubApplicationResponseDto.builder()
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
