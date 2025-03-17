package com.faster.order.app.order.application.dto.response;

import com.faster.order.app.order.domain.enums.OrderStatus;
import java.util.UUID;

public record CancelOrderApplicationResponseDto(
    UUID orderId,
    OrderStatus status
) {

  public static CancelOrderApplicationResponseDto of(UUID orderId, OrderStatus status) {
    return new CancelOrderApplicationResponseDto(orderId, status);
  }
}
