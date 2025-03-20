package com.faster.delivery.app.delivery.application.dto;

import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute;
import java.util.UUID;
import lombok.Builder;

@Builder
public record HubRouteDto(
    UUID sourceHubId,
    UUID destinationHubId,
    Long expectedDistanceM,
    Long expectedTimeMin
) {
  public DeliveryRoute toDeliveryRoute() {
    return DeliveryRoute.builder()
        .sourceHubId(sourceHubId)
        .destinationHubId(destinationHubId)
        .expectedDistanceM(expectedDistanceM)
        .expectedTimeMin(expectedTimeMin)
        .build();
  }
}
