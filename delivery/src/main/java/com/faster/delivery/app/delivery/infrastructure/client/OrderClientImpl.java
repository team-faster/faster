package com.faster.delivery.app.delivery.infrastructure.client;

import com.faster.delivery.app.delivery.application.OrderClient;
import com.faster.delivery.app.delivery.application.dto.OrderUpdateApplicationRequestDto;
import com.faster.delivery.app.delivery.application.dto.OrderUpdateApplicationResponseDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.order.OrderUpdateResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderClientImpl implements OrderClient {

  private final OrderFeignClient orderFeignClient;

  @Override
  public OrderUpdateApplicationResponseDto updateOrderStatus(
      UUID orderId, OrderUpdateApplicationRequestDto requestDto) {

    OrderUpdateResponseDto updateDto =
        orderFeignClient.updateOrderStatus(orderId, requestDto).getBody().data();
    return updateDto.toApplicationDto();
  }
}
