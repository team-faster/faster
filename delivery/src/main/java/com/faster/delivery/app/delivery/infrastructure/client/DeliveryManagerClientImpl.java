package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.application.DeliveryManagerClient;
import com.faster.delivery.app.delivery.application.dto.AssignDeliveryManagerApplicationResponse;
import com.faster.delivery.app.delivery.application.dto.DeliveryManagerDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager.AssignDeliveryManagerFeignRequestDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager.DeliveryManagerGetResponseDto;
import com.faster.delivery.app.delivery.infrastructure.client.type.DeliveryManagerType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryManagerClientImpl implements DeliveryManagerClient {

  private final DeliveryManagerFeignClient deliveryManagerFeignClient;

  public DeliveryManagerDto getDeliveryManagerData(Long deliveryManagerId) {

    ResponseEntity<ApiResponse<DeliveryManagerGetResponseDto>> deliveryManagerData =
        deliveryManagerFeignClient.getDeliveryManagerData(deliveryManagerId);
    DeliveryManagerGetResponseDto data = deliveryManagerData.getBody().data();
    return data.toDeliveryManagerDto();
  }

  @Override
  public AssignDeliveryManagerApplicationResponse assignCompanyDeliveryManager(UUID hubId,  DeliveryManagerType type, int requiredAssignManagerCount) {
    return deliveryManagerFeignClient.assignCompanyDeliveryManager(
            AssignDeliveryManagerFeignRequestDto.of(hubId, type, requiredAssignManagerCount))
        .getBody().data().to();
  }

}
