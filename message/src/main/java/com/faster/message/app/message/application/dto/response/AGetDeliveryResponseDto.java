package com.faster.message.app.message.application.dto.response;

import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AGetDeliveryResponseDto(
    List<ADeliveryRouteDto> deliveryRouteList

) {
  @Builder
  public record ADeliveryRouteDto(
      UUID deliveryManagerId, // 배송 담당자 id
      UUID sourceHubId // 출발지 허브 id
  ) {
  }
}
