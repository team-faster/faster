package com.faster.hub.app.hub.application.dto;

import com.faster.hub.app.hub.presentation.dto.CreateHubRequestDto;
import com.faster.hub.app.hub.presentation.dto.UpdateHubRequestDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateHubApplicationRequestDto(
    UUID id,
    String name,
    String address,
    String latitude,
    String longitude
) {

  public static UpdateHubApplicationRequestDto of(UUID id, UpdateHubRequestDto dto) {
    return UpdateHubApplicationRequestDto.builder()
        .id(id)
        .name(dto.name())
        .address(dto.address())
        .latitude(dto.latitude())
        .longitude(dto.longitude())
        .build();
  }
}