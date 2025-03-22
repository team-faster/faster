package com.faster.delivery.app.deliverymanager.application.dto;

import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerDetailDto(
    Long deliveryManagerId,
    String deliveryManagerName,
    UUID hubId,
    String type,
    Integer deliverySequenceNumber,
    String createdAt,
    String updatedAt,
    Long createdBy,
    Long updatedBy
) {

  public static DeliveryManagerDetailDto from(DeliveryManager deliveryManager) {
    return DeliveryManagerDetailDto.builder()
        .deliveryManagerId(deliveryManager.getId())
        .deliveryManagerName(deliveryManager.getUserName())
        .hubId(deliveryManager.getHubId())
        .type(deliveryManager.getType().name())
        .deliverySequenceNumber(deliveryManager.getDeliverySequenceNumber())
        .createdAt(deliveryManager.getCreatedAt().toString())
        .createdBy(deliveryManager.getCreatedBy())
        .updatedBy(deliveryManager.getUpdatedBy())
        .updatedAt(deliveryManager.getUpdatedAt() == null ? null : deliveryManager.getUpdatedAt().toString())
        .build();
  }
}
