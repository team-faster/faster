package com.faster.order.app.order.infrastructure.feign.dto.response;

import com.faster.order.app.order.application.dto.response.SaveDeliveryApplicationResponseDto;
import java.util.UUID;

public record SaveDeliveryResponseDto(
    UUID deliveryId
) {

  public SaveDeliveryApplicationResponseDto toApplicationDto() {
    return SaveDeliveryApplicationResponseDto.builder()
        .deliveryId(this.deliveryId)
        .build();
  }
}
