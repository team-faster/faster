package com.faster.message.app.message.infrastructure.fegin.dto.response;


import java.util.List;

public record IGetOrderResponseDto(
    String request,
    List<OrderItemDetailResponseDto> orderItems
) {
  public record OrderItemDetailResponseDto(
      String name,
      int quantity
  ) {
  }
}