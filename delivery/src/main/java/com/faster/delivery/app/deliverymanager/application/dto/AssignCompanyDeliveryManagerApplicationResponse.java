package com.faster.delivery.app.deliverymanager.application.dto;

import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AssignCompanyDeliveryManagerApplicationResponse (
    UUID deliveryManagerId,
    UUID hubId,
    String type,
    Integer deliverySequenceNumber,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long createdBy,
    Long updatedBy,
    Long userId
){

  public static AssignCompanyDeliveryManagerApplicationResponse from(
      DeliveryManager deliveryManager) {
    return AssignCompanyDeliveryManagerApplicationResponse.builder()
        .deliveryManagerId(deliveryManager.getId())
        .hubId(deliveryManager.getHubId())
        .type(deliveryManager.getType().name())
        .deliverySequenceNumber(deliveryManager.getDeliverySequenceNumber())
        .createdAt(deliveryManager.getCreatedAt())
        .createdBy(deliveryManager.getCreatedBy())
        .updatedBy(deliveryManager.getUpdatedBy())
        .updatedAt(deliveryManager.getUpdatedAt() == null
            ? null : deliveryManager.getUpdatedAt())
        .userId(deliveryManager.getUserId())
        .build();
  }
}
