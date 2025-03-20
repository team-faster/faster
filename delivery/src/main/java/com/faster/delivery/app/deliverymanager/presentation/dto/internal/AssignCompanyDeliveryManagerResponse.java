package com.faster.delivery.app.deliverymanager.presentation.dto.internal;

import com.faster.delivery.app.deliverymanager.application.dto.AssignCompanyDeliveryManagerApplicationResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AssignCompanyDeliveryManagerResponse(
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

  public static AssignCompanyDeliveryManagerResponse from(
      AssignCompanyDeliveryManagerApplicationResponse deliveryManagerDetailDto) {
    return AssignCompanyDeliveryManagerResponse.builder()
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