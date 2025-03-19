package com.faster.hub.app.hub.presentation.dto.response;

import com.faster.hub.app.hub.application.usecase.dto.response.UpdateHubRoutesApplicationResponseDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record UpdateHubRoutesResponseDto (
    List<HubRouteInfo> hubRouteInfos
) {

  public static UpdateHubRoutesResponseDto from(
      UpdateHubRoutesApplicationResponseDto updateHubRoutesApplicationResponseDto) {
    return UpdateHubRoutesResponseDto.builder()
        .hubRouteInfos(updateHubRoutesApplicationResponseDto.hubRouteInfos()
            .stream().map(HubRouteInfo::from).collect(Collectors.toUnmodifiableList()))
        .build();
  }

  @Builder
  public record HubRouteInfo(
      UUID id, UUID sourceHubId, UUID destinationHubId, Long distanceMeters, Long durationMinutes
  ) {

    public static HubRouteInfo from(
        UpdateHubRoutesApplicationResponseDto.HubRouteInfo hubRouteInfo) {
      return HubRouteInfo.builder()
          .id(hubRouteInfo.id())
          .sourceHubId(hubRouteInfo.sourceHubId())
          .destinationHubId(hubRouteInfo.destinationHubId())
          .distanceMeters(hubRouteInfo.distanceMeters())
          .durationMinutes(hubRouteInfo.durationMinutes())
          .build();
    }
  }
}
