package com.faster.delivery.app.delivery.presentaion.dto.internal;

import com.faster.delivery.app.delivery.application.dto.DeliveryUpdateDto;
import lombok.Builder;

@Builder
public record DeliveryUpdateInternalRequestDto(
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
