package com.faster.order.app.order.presentation.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderItemDetailResponseDto(
    UUID id,
    UUID productId,
    String name,
    Integer quantity,
    BigDecimal price
) {
}
