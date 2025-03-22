package com.faster.delivery.app.delivery.application.dto;

import com.faster.delivery.app.delivery.domain.entity.Delivery;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryGetElementDto(
  UUID deliveryId,
  Long companyDeliveryManagerId,
  UUID sourceHubId,
  UUID destinationHubId,
  UUID receiptCompanyId,
  String address,
  String recipientName,
  String status,
  String createdAt,
  String updatedAt
) {

  public static DeliveryGetElementDto from(Delivery delivery) {

    return DeliveryGetElementDto.builder()
        .deliveryId(delivery.getId())
        .companyDeliveryManagerId(delivery.getCompanyDeliveryManagerId())
        .sourceHubId(delivery.getSourceHubId())
        .destinationHubId(delivery.getDestinationHubId())
        .receiptCompanyId(delivery.getReceiptCompanyId())
        .address(delivery.getReceiptCompanyAddress())
        .recipientName(delivery.getRecipientName())
        .status(delivery.getStatus().toString())
        .createdAt(delivery.getCreatedAt().toString())
        .updatedAt(delivery.getUpdatedAt().toString())
        .build();
  }
}
