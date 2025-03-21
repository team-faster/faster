package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.application.DeliveryManagerClient;
import com.faster.delivery.app.delivery.application.dto.DeliveryManagerDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager.DeliveryManagerGetResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryManagerClientImpl implements DeliveryManagerClient {

  private final DeliveryManagerFeignClient deliveryManagerFeignClient;

  public DeliveryManagerDto getDeliveryManagerData(UUID deliveryManagerId) {

    ResponseEntity<ApiResponse<DeliveryManagerGetResponseDto>> deliveryManagerData =
        deliveryManagerFeignClient.getDeliveryManagerData(deliveryManagerId);
    DeliveryManagerGetResponseDto data = deliveryManagerData.getBody().data();
    return data.toDeliveryManagerDto();
  }

  @Override
  public DeliveryManagerDto assignCompanyDeliveryManager(UUID companyId) {
    return deliveryManagerFeignClient.assignCompanyDeliveryManager(companyId)
        .getBody().data().toDeliveryManagerDto();
  }

  @Override
  public DeliveryManagerDto getDeliveryManagerByUserId(Long userId) {
    DeliveryManagerGetResponseDto deliveryManagerDto =
        deliveryManagerFeignClient.getDeliveryManagerByUserId(userId).getBody().data();
    return deliveryManagerDto.toDeliveryManagerDto();
  }
}
