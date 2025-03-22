package com.faster.delivery.app.delivery.application.dto;

import lombok.Builder;

@Builder
public record DeliveryManagerDto(
    Long deliveryManagerId,
    String deliveryManagerName,
    String hubId,
    String type,
    Integer deliverySequenceNumber,
    String createdAt,
    String modifiedAt,
    String createdBy,
    String modifiedBy
) {

}
