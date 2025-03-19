package com.faster.hub.app.hub.presentation.dto.response;

import com.faster.hub.app.hub.application.usecase.dto.response.GetHubsApplicationResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;

public record GetHubsInternalResponseDto(
    List<HubInfo> hubInfos
){


  public static GetHubsInternalResponseDto from(GetHubsApplicationResponseDto dto) {
    return new GetHubsInternalResponseDto(
        dto.hubInfos().stream().map(HubInfo::from).collect(Collectors.toList()));
  }

  @Builder
  private record HubInfo(
      UUID id,
      Long managerId,
      String name,
      String address,
      String latitude,
      String longitude,
      Long createdBy,
      LocalDateTime createdAt
  ) {

    public static HubInfo from(GetHubsApplicationResponseDto.HubInfo info) {
      return HubInfo.builder()
          .id(info.id())
          .managerId(info.managerId())
          .name(info.name())
          .address(info.address())
          .latitude(info.latitude())
          .longitude(info.longitude())
          .createdBy(info.createdBy())
          .createdAt(info.createdAt())
          .build();
    }
  }
}