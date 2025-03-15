package com.faster.order.app.order.application.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderItemDetailApplicationResponseDto(
    UUID id,
    UUID productId,
    String name,
    Integer quantity,
    BigDecimal price
) {
}
