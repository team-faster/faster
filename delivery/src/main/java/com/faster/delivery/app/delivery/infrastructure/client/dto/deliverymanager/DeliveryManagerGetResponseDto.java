package com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager;

import com.faster.delivery.app.delivery.application.dto.DeliveryManagerDto;
import lombok.Builder;

@Builder
public record DeliveryManagerGetResponseDto(
  Long deliveryManagerId,
  String hubId,
  String type,
  Integer deliverySequenceNumber,
  String createdAt,
  String modifiedAt,
  String createdBy,
  String modifiedBy
) {
  public DeliveryManagerDto toDeliveryManagerDto() {
    return DeliveryManagerDto.builder()
        .deliveryManagerId(this.deliveryManagerId)
        .hubId(this.hubId)
        .type(this.type)
        .deliverySequenceNumber(this.deliverySequenceNumber)
        .createdAt(this.createdAt)
        .modifiedAt(this.modifiedAt)
        .createdBy(this.createdBy)
        .modifiedBy(this.modifiedBy)
        .build();
  }
}
