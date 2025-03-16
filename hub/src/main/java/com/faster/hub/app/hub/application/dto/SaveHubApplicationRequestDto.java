package com.faster.hub.app.hub.application.dto;

import com.faster.hub.app.hub.domain.entity.Hub;
import com.faster.hub.app.hub.presentation.dto.SaveHubRequestDto;
import lombok.Builder;

@Builder
public record SaveHubApplicationRequestDto(
    String name,
    String address,
    String latitude,
    String longitude
) {


  public static SaveHubApplicationRequestDto from(SaveHubRequestDto dto) {
    return SaveHubApplicationRequestDto.builder()
        .name(dto.name())
        .address(dto.address())
        .latitude(dto.latitude())
        .longitude(dto.longitude())
        .build();
  }

  public Hub toEntity() {
    return Hub.builder()
        .name(this.name)
        .address(this.address)
        .latitude(this.latitude)
        .longitude(this.longitude)
        .build();
  }
}
