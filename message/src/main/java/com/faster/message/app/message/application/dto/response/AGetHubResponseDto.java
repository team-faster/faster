package com.faster.message.app.message.application.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record AGetHubResponseDto(
    List<HubInfo> hubInfos
) {

  @Builder
  public static record HubInfo(
      Long managerId,
      String name,
      String address
  ) {}
}