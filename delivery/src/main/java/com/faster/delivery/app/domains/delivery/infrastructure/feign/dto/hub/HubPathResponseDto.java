package com.faster.delivery.app.domains.delivery.infrastructure.feign.dto.hub;

import com.faster.delivery.app.domains.delivery.domain.entity.DeliveryRoute;
import com.faster.delivery.app.domains.delivery.domain.entity.DeliveryRoute.Status;
import com.faster.delivery.app.domains.delivery.domain.entity.DeliveryRoute.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record HubPathResponseDto(
  List<RouteDto> contents
) {

  @Builder
  public record RouteDto(
      Integer sequence,
      UUID sourceHubId,
      UUID destinationHubId,
      Long expectedDistanceM,
      Long expectedTimeMin
  ) {

  }

  public static List<DeliveryRoute> to (HubPathResponseDto hubPathResponseDto) {
    ArrayList<DeliveryRoute> deliveryRoutes = new ArrayList<>();
    for (RouteDto routeDto : hubPathResponseDto.contents) {
      DeliveryRoute deliveryRoute = DeliveryRoute.builder()
          .sequence(routeDto.sequence())
          .sourceHubId(routeDto.sourceHubId())
          .destinationHubId(routeDto.destinationHubId())
          .expectedDistanceM(routeDto.expectedDistanceM())
          .expectedTimeMin(routeDto.expectedTimeMin())
          .type(Type.TO_HUB)
          .status(Status.PENDING_TRANSFER)
          .build();
      deliveryRoutes.add(deliveryRoute);
    }
    return deliveryRoutes;
  }
}
