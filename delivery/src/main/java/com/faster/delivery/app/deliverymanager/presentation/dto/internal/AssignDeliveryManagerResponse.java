package com.faster.delivery.app.deliverymanager.presentation.dto.internal;

import com.faster.delivery.app.deliverymanager.application.dto.AssignDeliveryManagerApplicationResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AssignDeliveryManagerResponse(
    List<DeliveryManagerInfo> deliveryManagers
) {

  public static AssignDeliveryManagerResponse from(
      AssignDeliveryManagerApplicationResponse response){
    return AssignDeliveryManagerResponse.builder()
        .deliveryManagers(response.deliveryManagers().stream()
            .map(DeliveryManagerInfo::from).toList()).build();
  }

  @Builder
  record DeliveryManagerInfo(
      UUID deliveryManagerId,
      UUID hubId,
      String type,
      Integer deliverySequenceNumber,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      Long createdBy,
      Long updatedBy,
      Long userId
  ) {

    public static DeliveryManagerInfo from(
        AssignDeliveryManagerApplicationResponse.DeliveryManagerInfo deliveryManagerDetailDto) {
      return DeliveryManagerInfo.builder()
          .deliveryManagerId(deliveryManagerDetailDto.deliveryManagerId())
          .hubId(deliveryManagerDetailDto.hubId())
          .type(deliveryManagerDetailDto.type())
          .deliverySequenceNumber(deliveryManagerDetailDto.deliverySequenceNumber())
          .createdAt(deliveryManagerDetailDto.createdAt())
          .updatedAt(deliveryManagerDetailDto.updatedAt())
          .createdBy(deliveryManagerDetailDto.createdBy())
          .updatedBy(deliveryManagerDetailDto.updatedBy())
          .userId(deliveryManagerDetailDto.userId())
          .build();
    }
  }
}