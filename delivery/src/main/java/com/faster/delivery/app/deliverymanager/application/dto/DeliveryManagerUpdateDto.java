package com.faster.delivery.app.deliverymanager.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerUpdateDto(
    String type,
    Integer deliverySequenceNumber,
    UUID hubId
) {

}
