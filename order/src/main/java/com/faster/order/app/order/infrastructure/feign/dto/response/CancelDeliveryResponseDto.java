package com.faster.order.app.order.infrastructure.feign.dto.response;

import com.faster.order.app.order.application.dto.response.CancelDeliveryApplicationResponseDto;
import java.util.UUID;

public record CancelDeliveryResponseDto(
    UUID deliveryId
) {

  public CancelDeliveryApplicationResponseDto toApplicationDto() {

    return CancelDeliveryApplicationResponseDto.builder()
        .deliveryId(deliveryId)
        .build();
  }
}
