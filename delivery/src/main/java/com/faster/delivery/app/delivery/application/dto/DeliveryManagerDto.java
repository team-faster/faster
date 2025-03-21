package com.faster.delivery.app.delivery.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerDto(
    UUID deliveryManagerId,
    String deliveryManagerName,
    Long userId,
    String hubId,
    String type,
    Integer deliverySequenceNumber,
    String createdAt,
    String modifiedAt,
    String createdBy,
    String modifiedBy
) {

}
