package com.faster.order.app.order.presentation.dto.response;

import com.faster.order.app.order.application.dto.response.CancelOrderApplicationResponseDto;
import com.faster.order.app.order.domain.enums.OrderStatus;
import java.util.UUID;

public record CancelOrderResponseDto(
    UUID orderId,
    OrderStatus status
) {

  public static CancelOrderResponseDto from(CancelOrderApplicationResponseDto responseDto) {
    return new CancelOrderResponseDto(responseDto.orderId(), responseDto.status());
  }
}
