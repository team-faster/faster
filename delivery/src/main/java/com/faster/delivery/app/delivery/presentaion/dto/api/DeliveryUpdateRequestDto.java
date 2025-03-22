package com.faster.delivery.app.delivery.presentaion.dto.api;

import com.faster.delivery.app.delivery.application.dto.DeliveryUpdateDto;
import lombok.Builder;

@Builder
public record DeliveryUpdateRequestDto (
  Long companyDeliveryManagerId,
  String status
) {

  public DeliveryUpdateDto toDeliveryUpdateDto() {
    return DeliveryUpdateDto.builder()
        .companyDeliveryManagerId(this.companyDeliveryManagerId)
        .status(this.status)
        .build();
  }
}
