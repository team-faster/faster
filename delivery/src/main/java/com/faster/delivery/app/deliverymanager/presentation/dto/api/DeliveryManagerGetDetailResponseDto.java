package com.faster.delivery.app.deliverymanager.presentation.dto.api;

import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerDetailDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerGetDetailResponseDto(
  UUID deliveryManagerId,
  UUID hubId,
  String type,
  Integer deliverySequenceNumber,
  String createdAt,
  String updatedAt,
  Long createdBy,
  Long updatedBy,
  Long userId
) {

  public static DeliveryManagerGetDetailResponseDto from(DeliveryManagerDetailDto deliveryManagerDetailDto) {
    return DeliveryManagerGetDetailResponseDto.builder()
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
