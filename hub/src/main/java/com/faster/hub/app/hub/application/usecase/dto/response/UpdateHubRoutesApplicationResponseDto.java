package com.faster.hub.app.hub.application.usecase.dto.response;

import com.faster.hub.app.hub.domain.entity.HubRoute;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record UpdateHubRoutesApplicationResponseDto (
    List<HubRouteInfo> hubRouteInfos
){

  public static UpdateHubRoutesApplicationResponseDto from(Collection<HubRoute> hubRoutes) {
    return UpdateHubRoutesApplicationResponseDto.builder()
        .hubRouteInfos(
            hubRoutes.stream().map(HubRouteInfo::from).toList())
        .build();
  }

  @Builder
  public record HubRouteInfo(
      UUID id, UUID sourceHubId, UUID destinationHubId, Long distanceMeters, Long durationMinutes
  ){

    public static HubRouteInfo from(HubRoute hubRoute) {
      return HubRouteInfo.builder()
          .id(hubRoute.getId())
          .sourceHubId(hubRoute.getSourceHub().getId())
          .destinationHubId(hubRoute.getDestinationHub().getId())
          .distanceMeters(hubRoute.getDistanceMeters())
          .durationMinutes(hubRoute.getDurationMinutes())
          .build();
    }
  }
}
