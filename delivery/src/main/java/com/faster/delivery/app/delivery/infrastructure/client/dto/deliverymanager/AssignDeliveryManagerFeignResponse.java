package com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager;

import com.faster.delivery.app.delivery.application.dto.AssignDeliveryManagerApplicationResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AssignDeliveryManagerFeignResponse(
    List<DeliveryManagerInfo> deliveryManagers
) {

  public AssignDeliveryManagerApplicationResponse to(){
    return AssignDeliveryManagerApplicationResponse.builder()
        .deliveryManagers(deliveryManagers.stream()
            .map(info -> info.to()).toList()).build();
  }

  @Builder
  public record DeliveryManagerInfo(
      Long deliveryManagerId,
      UUID hubId,
      String type,
      Integer deliverySequenceNumber,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      Long createdBy,
      Long updatedBy,
      Long userId
  ) {

    public AssignDeliveryManagerApplicationResponse.DeliveryManagerInfo to() {
      return AssignDeliveryManagerApplicationResponse.DeliveryManagerInfo.builder()
          .deliveryManagerId(this.deliveryManagerId)
          .hubId(hubId)
          .type(type)
          .deliverySequenceNumber(deliverySequenceNumber)
          .createdAt(createdAt)
          .updatedAt(updatedAt)
          .createdBy(createdBy)
          .updatedBy(updatedBy)
          .userId(userId)
          .build();
    }
  }
}