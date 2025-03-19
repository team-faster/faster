package com.faster.hub.app.hub.presentation.dto.response;

import com.faster.hub.app.hub.application.usecase.dto.response.SaveHubApplicationResponseDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SaveHubResponseDto(
    UUID id,
    String name,
    String address,
    String latitude,
    String longitude,
    Long createBy,
    LocalDateTime createAt
) {

  public static SaveHubResponseDto from(SaveHubApplicationResponseDto dto) {
    return SaveHubResponseDto.builder()
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
