package com.faster.delivery.app.delivery.infrastructure.client.dto.hub;

import com.faster.delivery.app.delivery.application.dto.HubRouteDto;
import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute;
import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute.Status;
import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record HubPathResponseDto(
  List<RouteResponseDto> contents
) {
  @Builder
  public record RouteResponseDto(
      Integer sequence,
      UUID sourceHubId,
      UUID destinationHubId,
      Long expectedDistanceM,
      Long expectedTimeMin
  ) {
    public HubRouteDto toHubRouteDto() {
      return HubRouteDto.builder()
          .sequence(sequence)
          .sourceHubId(sourceHubId)
          .destinationHubId(destinationHubId)
          .expectedDistanceM(expectedDistanceM)
          .expectedTimeMin(expectedTimeMin)
          .build();
    }
  }

  public List<HubRouteDto> toHubRouteDtoList() {
    ArrayList<HubRouteDto> hubRouteDtoList = new ArrayList<>();
    for (RouteResponseDto routeResponseDto : contents) {
      hubRouteDtoList.add(routeResponseDto.toHubRouteDto());
    }
    return hubRouteDtoList;
  }

  public List<DeliveryRoute> toDeliveryRouteList(Long userId) {
    ArrayList<DeliveryRoute> deliveryRoutes = new ArrayList<>();
    for (RouteResponseDto routeResponseDto : this.contents) {
      DeliveryRoute deliveryRoute = DeliveryRoute.builder()
          .sequence(routeResponseDto.sequence())
          .sourceHubId(routeResponseDto.sourceHubId())
          .destinationHubId(routeResponseDto.destinationHubId())
          .expectedDistanceM(routeResponseDto.expectedDistanceM())
          .expectedTimeMin(routeResponseDto.expectedTimeMin())
          .type(Type.TO_HUB)
          .status(Status.PENDING_TRANSFER)
          .build();
      deliveryRoute.createdBy(userId);
      deliveryRoutes.add(deliveryRoute);
    }
    return deliveryRoutes;
  }
}
