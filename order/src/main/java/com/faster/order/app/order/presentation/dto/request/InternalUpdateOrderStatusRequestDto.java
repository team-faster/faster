package com.faster.order.app.order.presentation.dto.request;

public record InternalUpdateOrderStatusRequestDto(
    OrderStatus status
) {

  enum OrderStatus {
    ACCEPTED, CONFIRMED, DISPATCHED, DELIVERED, COMPLETED, CANCELED
  }
}
