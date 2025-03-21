package com.faster.delivery.app.deliverymanager.application.dto;

import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AssignDeliveryManagerApplicationResponse(
    List<DeliveryManagerInfo> deliveryManagers
){

  public static AssignDeliveryManagerApplicationResponse from(List<DeliveryManager> lists){
    return AssignDeliveryManagerApplicationResponse.builder()
        .deliveryManagers(lists.stream().map(DeliveryManagerInfo::from).toList())
        .build();
  }

  @Builder
  public record DeliveryManagerInfo(
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

    public static DeliveryManagerInfo from(DeliveryManager deliveryManager) {
      return DeliveryManagerInfo.builder()
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
}
