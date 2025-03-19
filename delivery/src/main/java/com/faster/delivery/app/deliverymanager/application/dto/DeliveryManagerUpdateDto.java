package com.faster.delivery.app.deliverymanager.application.dto;

import lombok.Builder;

@Builder
public record DeliveryManagerUpdateDto(
    String type,
    Integer deliverySequenceNumber
) {

}
