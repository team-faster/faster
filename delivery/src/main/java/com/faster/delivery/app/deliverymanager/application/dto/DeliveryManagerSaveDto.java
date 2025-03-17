package com.faster.delivery.app.deliverymanager.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerSaveDto(
    UUID hubId,
    String type,
    Long userId
) {
}
