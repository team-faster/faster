package com.faster.order.app.order.infrastructure;

import com.faster.order.app.order.application.client.DeliveryClient;
import com.faster.order.app.order.application.dto.request.SaveDeliveryApplicationRequestDto;
import com.faster.order.app.order.application.dto.response.CancelDeliveryApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.SaveDeliveryApplicationResponseDto;
import com.faster.order.app.order.infrastructure.feign.DeliveryFeignClient;
import com.faster.order.app.order.infrastructure.feign.dto.request.CancelDeliveryRequestDto;
import com.faster.order.app.order.infrastructure.feign.dto.request.SaveDeliveryRequestDto;
import com.faster.order.app.order.infrastructure.feign.dto.response.CancelDeliveryResponseDto;
import com.faster.order.app.order.infrastructure.feign.dto.response.SaveDeliveryResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryClientImpl implements DeliveryClient {
  private final DeliveryFeignClient deliveryFeignClient;

  @Override
  public SaveDeliveryApplicationResponseDto saveDelivery(
      SaveDeliveryApplicationRequestDto requestDto) {

    SaveDeliveryResponseDto responseDto =
        deliveryFeignClient.saveDelivery(SaveDeliveryRequestDto.from(requestDto)).getBody().data();
    return responseDto.toApplicationDto();
  }

  @Override
  public CancelDeliveryApplicationResponseDto cancelDelivery(UUID deliveryId) {

    CancelDeliveryResponseDto responseDto =
        deliveryFeignClient.updateDelivery(deliveryId, CancelDeliveryRequestDto.create()).getBody().data();
    return responseDto.toApplicationDto();
  }
}
