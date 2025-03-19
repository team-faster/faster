package com.faster.delivery.app.delivery.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryGetElementDto(
  UUID deliveryId,
  String status,
  String createdAt,
  String address
) {

}
