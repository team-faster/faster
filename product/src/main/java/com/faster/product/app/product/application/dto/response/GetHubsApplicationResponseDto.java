package com.faster.product.app.product.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetHubsApplicationResponseDto(
    List<HubInfo> hubInfos
){

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

  }
}
