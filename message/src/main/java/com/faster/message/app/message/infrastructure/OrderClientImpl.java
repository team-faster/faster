package com.faster.message.app.message.infrastructure;

import com.faster.message.app.message.application.client.OrderClient;
import com.faster.message.app.message.application.dto.response.AGetOrderResponseDto;
import com.faster.message.app.message.infrastructure.fegin.OrderFeignClient;
import com.faster.message.app.message.infrastructure.fegin.dto.response.IGetOrderResponseDto;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderClientImpl implements OrderClient {

  private final OrderFeignClient orderFeignClient;

  @Override
  public AGetOrderResponseDto getOrderByOrderId(UUID orderId) {
    IGetOrderResponseDto data = orderFeignClient.internalGetOrderById(orderId).getBody().data();

    if (data == null) {
      return null; // TODO: 에러 처리 추후에 진행
    }

    // infrastructure DTO ➔ application DTO
    return AGetOrderResponseDto.builder()
        .orderRequestMessage(data.request())
        .orderItems(data.orderItems().stream()
            .map(item -> new AGetOrderResponseDto.OrderItemDto(item.name(), item.quantity()))
            .collect(Collectors.toList()))
        .build();
  }
}
