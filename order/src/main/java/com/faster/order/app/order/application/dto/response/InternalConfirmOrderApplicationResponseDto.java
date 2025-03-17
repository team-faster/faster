package com.faster.order.app.order.application.dto.response;

import com.faster.order.app.order.domain.enums.OrderStatus;
import java.util.UUID;

public record InternalConfirmOrderApplicationResponseDto(
    UUID orderId,
    OrderStatus status
) {

  public static InternalConfirmOrderApplicationResponseDto of(UUID orderId, OrderStatus status) {
    return new InternalConfirmOrderApplicationResponseDto(orderId, status);
  }
}
