package com.faster.company.app.company.infrastructure.feign.dto.response;

import com.faster.company.app.company.application.dto.response.GetHubsApplicationResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

public record GetHubsResponseDto(
    List<HubInfo> hubInfos
){

  public GetHubsApplicationResponseDto toApplicationDto() {
    return GetHubsApplicationResponseDto.builder()
        .hubInfos(hubInfos.stream()
            .map(HubInfo::toApplicationDto)
            .toList()
        )
        .build();
  }

  @Builder
  record HubInfo(
      UUID id,
      Long managerId,
      String name,
      String address,
      String latitude,
      String longitude,
      Long createdBy,
      LocalDateTime createdAt
  ) {

    public GetHubsApplicationResponseDto.HubInfo toApplicationDto() {
      return GetHubsApplicationResponseDto.HubInfo.builder()
          .id(id)
          .managerId(managerId)
          .name(name)
          .address(address)
          .latitude(latitude)
          .longitude(longitude)
          .createdBy(createdBy)
          .createdAt(createdAt)
          .build();
    }
  }
}