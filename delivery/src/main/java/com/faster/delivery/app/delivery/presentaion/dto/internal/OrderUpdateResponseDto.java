package com.faster.delivery.app.delivery.presentaion.dto.internal;

import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderUpdateResponseDto(
  UUID orderId,
  String status
) {
}
