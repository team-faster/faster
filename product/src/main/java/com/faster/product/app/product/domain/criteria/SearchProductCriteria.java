package com.faster.product.app.product.domain.criteria;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchProductCriteria(
    UUID companyId,
    String companyName,
    String name,
    BigDecimal minPrice,
    BigDecimal maxPrice,
    LocalDateTime startCreatedAt,
    LocalDateTime endCreatedAt,
    Boolean isDeleted
) {

}
