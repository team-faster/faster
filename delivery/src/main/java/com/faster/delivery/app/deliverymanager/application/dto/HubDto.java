package com.faster.delivery.app.deliverymanager.application.dto;

import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.HubGetResponseDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record HubDto(
    UUID hubId,
    String name,
    String address,
    String latitude,
    String longitude
) {

  public static HubDto from(HubGetResponseDto responseDto) {
    return HubDto.builder()
        .hubId(responseDto.hubId())
        .name(responseDto.name())
        .address(responseDto.address())
        .latitude(responseDto.latitude())
        .longitude(responseDto.longitude())
        .build();
  }
}
