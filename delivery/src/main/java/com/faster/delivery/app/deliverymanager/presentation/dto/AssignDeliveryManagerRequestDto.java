package com.faster.delivery.app.deliverymanager.presentation.dto;

import com.faster.delivery.app.deliverymanager.application.dto.AssignDeliveryManagerApplicationRequestDto;
import com.faster.delivery.app.deliverymanager.application.type.DeliveryManagerType;
import java.util.UUID;

public record AssignDeliveryManagerRequestDto(
    UUID hubId,  DeliveryManagerType type, int requiredAssignManagerCount
){

  public AssignDeliveryManagerApplicationRequestDto toApplicationDto() {
    return AssignDeliveryManagerApplicationRequestDto.builder()
        .hubId(this.hubId)
        .type(this.type)
        .requiredAssignManagerCount(this.requiredAssignManagerCount)
        .build();
  }
}
