package com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager;

import com.faster.delivery.app.delivery.infrastructure.client.type.DeliveryManagerType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AssignDeliveryManagerFeignRequestDto(
    UUID hubId, DeliveryManagerType type, int requiredAssignManagerCount
){

  public static AssignDeliveryManagerFeignRequestDto of(
      UUID hubId,  DeliveryManagerType type, int requiredAssignManagerCount){
    return AssignDeliveryManagerFeignRequestDto.builder()
        .hubId(hubId)
        .type(type)
        .requiredAssignManagerCount(requiredAssignManagerCount)
        .build();
  }
}
