package com.faster.message.app.message.infrastructure.fegin.dto.response;


import java.util.List;

public record IGetOrderResponseDto(
    String request,
    List<OrderItemDetailResponseDto> orderItems
) {
  public static record OrderItemDetailResponseDto(
      String name,
      int quantity
  ) {
  }
}