package com.faster.message.app.message.infrastructure.fegin.dto.response;

import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record IGetDeliveryResponseDto(
    List<IDeliveryRouteDto> deliveryRouteList

) {
  @Builder
  public record IDeliveryRouteDto(
      UUID deliveryManagerId,
      UUID sourceHubId
  ) {
  }
}
