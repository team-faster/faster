package com.faster.hub.app.hub.application.usecase.dto.request;

import com.faster.hub.app.hub.domain.entity.Hub;
import lombok.Builder;

@Builder
public record SaveHubApplicationRequestDto(
    Long managerId,
    String name,
    String address,
    String latitude,
    String longitude
) {

  public Hub toEntity() {
    return Hub.builder()
        .managerId(this.managerId)
        .name(this.name)
        .address(this.address)
        .latitude(this.latitude)
        .longitude(this.longitude)
        .build();
  }
}
