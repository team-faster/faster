package com.faster.order.app.order.infrastructure.persistence.jpa.dto.response;

import com.faster.order.app.order.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderQuerydslResponseDto(
    UUID id,
    UUID supplierCompanyId,
    String supplierCompanyName,
    UUID receivingCompanyId,
    String receivingCompanyName,
    UUID deliveryId,
    String name,
    BigDecimal totalPrice,
    OrderStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
