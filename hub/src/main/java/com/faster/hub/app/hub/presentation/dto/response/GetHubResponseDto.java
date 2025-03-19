package com.faster.hub.app.hub.presentation.dto.response;

import com.faster.hub.app.hub.application.usecase.dto.response.GetHubApplicationResponseDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetHubResponseDto(
    UUID id,
    String name,
    String address,
    String latitude,
    String longitude,
    Long createBy,
    LocalDateTime createAt
) {

  public static GetHubResponseDto from(GetHubApplicationResponseDto dto) {
    return GetHubResponseDto.builder()
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
