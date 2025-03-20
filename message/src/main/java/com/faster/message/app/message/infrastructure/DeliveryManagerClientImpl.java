package com.faster.message.app.message.infrastructure;

import com.faster.message.app.message.application.client.DeliveryManagerClient;
import com.faster.message.app.message.application.dto.response.AGetDeliveryManagerResponseDto;
import com.faster.message.app.message.infrastructure.fegin.DeliveryManagerFeignClient;
import com.faster.message.app.message.infrastructure.fegin.dto.response.IGetDeliveryManagerResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryManagerClientImpl implements DeliveryManagerClient {

  private final DeliveryManagerFeignClient deliveryManagerFeignClient;

  @Override
  public AGetDeliveryManagerResponseDto getOrderByOrderId(UUID deliveryManagerId) {
    IGetDeliveryManagerResponseDto data =
        deliveryManagerFeignClient.getDeliveryManagerDetails(deliveryManagerId).getBody().data();

    if (data == null) {
      return null; // TODO: 에러 처리 추후에 진행
    }

    return AGetDeliveryManagerResponseDto.builder()
        .userId(data.userId())
        .build();
  }
}
