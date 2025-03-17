package com.faster.order.app.order.application.dto.response;

import java.util.UUID;

public record InternalUpdateOrderStatusApplicationResponseDto(
    UUID orderId,
    String status
) {

  public static InternalUpdateOrderStatusApplicationResponseDto of(UUID id, String status) {
    return new InternalUpdateOrderStatusApplicationResponseDto(id, status);
  }
}
