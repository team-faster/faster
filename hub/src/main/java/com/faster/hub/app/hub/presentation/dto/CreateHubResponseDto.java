package com.faster.hub.app.hub.presentation.dto;

import com.faster.hub.app.hub.application.dto.CreateHubResponseApplicationResponseDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateHubResponseDto(
    UUID id,
    String name,
    String address,
    String latitude,
    String longitude,
    Long createBy,
    LocalDateTime createAt
) {

  public static CreateHubResponseDto from(CreateHubResponseApplicationResponseDto dto) {
    return CreateHubResponseDto.builder()
        .id(dto.id())
        .name(dto.name())
        .address(dto.address())
        .latitude(dto.latitude())
        .longitude(dto.longitude())
        .createBy(dto.createBy())
        .createAt(dto.createAt())
        .build();
  }
}
