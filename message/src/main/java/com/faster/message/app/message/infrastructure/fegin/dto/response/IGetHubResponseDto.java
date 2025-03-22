package com.faster.message.app.message.infrastructure.fegin.dto.response;

import java.util.List;

public record IGetHubResponseDto(
    List<HubInfo> hubInfos

) {
  public record HubInfo(
      Long managerId,
      String name,
      String address
  ) {
  }
}
