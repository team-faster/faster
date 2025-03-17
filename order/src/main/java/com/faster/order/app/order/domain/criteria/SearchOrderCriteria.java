package com.faster.order.app.order.domain.criteria;

import com.faster.order.app.order.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SearchOrderCriteria(
    BigDecimal minTotalPrice,
    BigDecimal maxTotalPrice,
    String supplierCompanyName,
    String receivingCompanyName,
    String name,
    String address,
    String contact,
    OrderStatus status,
    Boolean isDeleted,
    LocalDateTime startCreatedAt,
    LocalDateTime endCreatedAt
) {
}
