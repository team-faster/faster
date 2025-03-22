package com.faster.delivery.app.delivery.application.dto;

import lombok.Builder;

@Builder
public record DeliveryRouteUpdateDto(
  Long realDistanceM,
  Long realTimeMin,
  String status,
  Long deliveryManagerId,
  String deliveryManagerName
) {
  public boolean isRealMeasurementUpdate(){
    if (this.realDistanceM != null
        && this.realTimeMin != null) {
      return true;
    }
    return false;
  }

  public boolean isStatusUpdate(){
    return this.status != null;
  }

  public boolean isDeliveryManagerUpdate(){
    if (this.deliveryManagerId != null
      && this.deliveryManagerName != null) {;
      return true;
    }
    return false;
  }
}
