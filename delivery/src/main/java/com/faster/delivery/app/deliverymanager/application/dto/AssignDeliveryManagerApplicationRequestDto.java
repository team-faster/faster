package com.faster.delivery.app.deliverymanager.application.dto;

import com.faster.delivery.app.deliverymanager.application.type.DeliveryManagerType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AssignDeliveryManagerApplicationRequestDto(
    UUID hubId, DeliveryManagerType type, int requiredAssignManagerCount
) {

}
