package com.faster.delivery.app.delivery.infrastructure.client.dto.hub;

import com.faster.delivery.app.delivery.application.dto.HubRouteDto;
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
}
