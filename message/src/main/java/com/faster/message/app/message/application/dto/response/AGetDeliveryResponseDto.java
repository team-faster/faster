package com.faster.message.app.message.application.dto.response;

import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AGetDeliveryResponseDto(
    List<ADeliveryRouteDto> deliveryRouteList

) {
  @Builder
  public record ADeliveryRouteDto(
      UUID deliveryManagerId,
      UUID sourceHubId
  ) {
  }
}
