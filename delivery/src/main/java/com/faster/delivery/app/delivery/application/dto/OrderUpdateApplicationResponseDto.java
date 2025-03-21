package com.faster.delivery.app.delivery.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderUpdateApplicationResponseDto(
  UUID orderId,
  String status
) {
}
