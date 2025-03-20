package com.faster.message.app.message.application.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record AGetOrderResponseDto(
    String orderRequestMessage, //
    List<OrderItemDto> orderItems
) {

  @Builder
  public static record OrderItemDto(
      String name,
      int quantity
  ) {}
}