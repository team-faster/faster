package com.faster.delivery.app.delivery.presentaion.dto.api;

import com.faster.delivery.app.delivery.application.dto.DeliveryRouteUpdateDto;
import java.util.UUID;

public record DeliveryRouteUpdateRequestDto(
    Long realDistanceM,
    Long realTimeMin,
    String status,
    UUID deliveryManagerId,
    String deliveryManagerName
) {
  public DeliveryRouteUpdateDto toDeliveryRouteUpdateDto() {
    return DeliveryRouteUpdateDto.builder()
        .realDistanceM(this.realDistanceM)
        .realTimeMin(this.realTimeMin)
        .status(this.status)
        .deliveryManagerId(this.deliveryManagerId)
        .deliveryManagerName(this.deliveryManagerName)
        .build();
  }
}
