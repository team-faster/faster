package com.faster.delivery.app.delivery.presentaion.dto;

import com.faster.delivery.app.delivery.application.dto.DeliveryUpdateDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryUpdateRequestDto (
  UUID companyDeliveryManagerId,
  String status
) {

  public DeliveryUpdateDto toDeliveryUpdateDto() {
    return DeliveryUpdateDto.builder()
        .companyDeliveryManagerId(this.companyDeliveryManagerId)
        .status(this.status)
        .build();
  }
}
