package com.faster.message.app.message.infrastructure.fegin.dto.response;


import java.util.List;

public record IGetOrderResponseDto(
    String request,
    List<OrderItemDto> orderItems
) {
  public static record OrderItemDto(
      String name,
      int quantity
  ) {}
}