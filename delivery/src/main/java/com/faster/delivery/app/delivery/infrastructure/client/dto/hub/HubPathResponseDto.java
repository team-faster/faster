package com.faster.delivery.app.delivery.infrastructure.client.dto.hub;

import com.faster.delivery.app.delivery.application.dto.HubRouteDto;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record HubPathResponseDto(
  List<RouteResponseDto> paths
) {

  public List<HubRouteDto> toHubRouteDtoList() {
    return paths.stream().map(path -> path.toHubRouteDto()).toList();
  }

  @Builder
  public record RouteResponseDto(
      UUID sourceHubId,
      UUID destinationHubId,
      Long distanceM,
      Long durationMin
  ) {
    public HubRouteDto toHubRouteDto() {
      return HubRouteDto.builder()
          .sourceHubId(sourceHubId)
          .destinationHubId(destinationHubId)
          .expectedDistanceM(distanceM)
          .expectedTimeMin(durationMin)
          .build();
    }
  }
}
