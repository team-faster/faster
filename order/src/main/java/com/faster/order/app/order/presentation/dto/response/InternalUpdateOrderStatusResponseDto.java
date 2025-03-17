package com.faster.order.app.order.presentation.dto.response;

import com.faster.order.app.order.application.dto.response.InternalUpdateOrderStatusApplicationResponseDto;
import java.util.UUID;

public record InternalUpdateOrderStatusResponseDto(
    UUID orderId,
    String status
) {

  public static InternalUpdateOrderStatusResponseDto from(
      InternalUpdateOrderStatusApplicationResponseDto applicationResponseDto) {
    return new InternalUpdateOrderStatusResponseDto(
        applicationResponseDto.orderId(), applicationResponseDto.status());
  }
}
