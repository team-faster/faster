package com.faster.order.app.order.presentation.dto.response;

import com.faster.order.app.order.application.dto.response.InternalConfirmOrderApplicationResponseDto;
import com.faster.order.app.order.domain.enums.OrderStatus;
import java.util.UUID;

public record InternalConfirmOrderResponseDto(
    UUID orderId,
    OrderStatus status
) {

  public static InternalConfirmOrderResponseDto from(
      InternalConfirmOrderApplicationResponseDto responseDto) {
    return new InternalConfirmOrderResponseDto(responseDto.orderId(), responseDto.status());
  }
}
