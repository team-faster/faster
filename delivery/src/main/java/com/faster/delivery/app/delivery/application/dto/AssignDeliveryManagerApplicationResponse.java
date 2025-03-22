package com.faster.delivery.app.delivery.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AssignDeliveryManagerApplicationResponse(
    List<DeliveryManagerInfo> deliveryManagers
) {

  @Builder
  public record DeliveryManagerInfo(
      Long deliveryManagerId,
      String deliveryManagerName,
      UUID hubId,
      String type,
      Integer deliverySequenceNumber,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      Long createdBy,
      Long updatedBy,
      Long userId
  ) {

  }
}