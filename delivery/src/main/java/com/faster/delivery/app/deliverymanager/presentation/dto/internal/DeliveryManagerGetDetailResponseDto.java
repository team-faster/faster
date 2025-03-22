package com.faster.delivery.app.deliverymanager.presentation.dto.internal;

import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerDetailDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerGetDetailResponseDto(
  Long deliveryManagerId,
  UUID hubId,
  String type,
  Integer deliverySequenceNumber,
  String createdAt,
  String updatedAt,
  Long createdBy,
  Long updatedBy
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
        .build();
  }
}
