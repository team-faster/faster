package com.faster.delivery.app.delivery.presentaion.dto.api;

import com.faster.delivery.app.delivery.application.dto.DeliveryRouteUpdateDto;

public record DeliveryRouteUpdateRequestDto(
    Long realDistanceM,
    Long realTimeMin,
    String status,
    Long deliveryManagerId,
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
