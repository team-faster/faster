package com.faster.message.app.message.infrastructure;

import com.faster.message.app.message.application.client.DeliveryClient;
import com.faster.message.app.message.application.dto.response.AGetDeliveryResponseDto;
import com.faster.message.app.message.infrastructure.fegin.DeliveryFeignClient;
import com.faster.message.app.message.infrastructure.fegin.dto.response.IGetDeliveryResponseDto;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryClientImpl implements DeliveryClient {

  private final DeliveryFeignClient deliveryFeignClient;

  @Override
  public AGetDeliveryResponseDto getDeliveryByOrderId(UUID deliveryId) {
    IGetDeliveryResponseDto data = deliveryFeignClient.getDeliveryDetail(deliveryId).getBody().data();

    if (data == null) {
      return null; // TODO: 에러 처리 추후에 진행
    }


    return AGetDeliveryResponseDto.builder()
        .deliveryRouteList(data.deliveryRouteList().stream()
            .map(route -> AGetDeliveryResponseDto.ADeliveryRouteDto.builder()
                .deliveryManagerId(route.deliveryManagerId())
                .sourceHubId(route.sourceHubId())
                .build()
            )
            .collect(Collectors.toList())
        )
        .build();
  }
}
