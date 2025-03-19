package com.faster.hub.app.hub.application.usecase.dto.response;

import com.faster.hub.app.hub.domain.entity.Hub;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;

public record GetHubsApplicationInternalResponseDto(
    List<HubInfo> hubInfos
){

  public static GetHubsApplicationInternalResponseDto from(List<Hub> hubs){
    return new GetHubsApplicationInternalResponseDto(
        hubs.stream().map(HubInfo::from).collect(Collectors.toList()));
  }

  @Builder
  public record HubInfo(
      UUID id,
      Long managerId,
      String name,
      String address,
      String latitude,
      String longitude,
      Long createdBy,
      LocalDateTime createdAt
  ) {

    public static HubInfo from(
        Hub hub) {
      return HubInfo.builder()
          .id(hub.getId())
          .managerId(hub.getManagerId())
          .name(hub.getName())
          .address(hub.getAddress())
          .latitude(hub.getLatitude())
          .longitude(hub.getLongitude())
          .createdBy(hub.getCreatedBy())
          .createdAt(hub.getCreatedAt())
          .build();
    }
  }
}
