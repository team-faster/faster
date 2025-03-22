package com.faster.message.app.message.application.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record AGetOrderResponseDto(
    String orderRequestMessage, //
    List<OrderItemDetailResponseDto> orderItems
) {

  @Builder
  public record OrderItemDetailResponseDto(
      String name,
      int quantity
  ) {}
}