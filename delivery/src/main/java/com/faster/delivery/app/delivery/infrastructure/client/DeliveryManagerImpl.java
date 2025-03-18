package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.application.DeliveryManagerClient;
import com.faster.delivery.app.delivery.application.dto.DeliveryManagerDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager.DeliveryManagerGetResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryManagerImpl implements DeliveryManagerClient {

  private final DeliveryManagerFeignClient deliveryManagerFeignClient;

  public DeliveryManagerDto getDeliveryManagerData(UUID deliveryManagerId) {
    ApiResponse<DeliveryManagerGetResponseDto> deliveryManagerData =
        deliveryManagerFeignClient.getDeliveryManagerData(deliveryManagerId);
    DeliveryManagerGetResponseDto data = deliveryManagerData.data();
    return data.toDeliveryManagerDto();
  }
}
