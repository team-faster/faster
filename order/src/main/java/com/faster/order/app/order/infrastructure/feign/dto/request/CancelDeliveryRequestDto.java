package com.faster.order.app.order.infrastructure.feign.dto.request;

import lombok.Builder;

@Builder
public record CancelDeliveryRequestDto(
  DeliveryStatus status
) {
  enum DeliveryStatus {
    CANCELLED
  }

  public static CancelDeliveryRequestDto create() {
    return CancelDeliveryRequestDto.builder()
        .status(DeliveryStatus.CANCELLED)
        .build();
  }
}
