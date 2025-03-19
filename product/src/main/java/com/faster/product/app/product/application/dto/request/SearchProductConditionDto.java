package com.faster.product.app.product.application.dto.request;

import com.faster.product.app.product.domain.criteria.SearchProductCriteria;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchProductConditionDto(
    UUID companyId,
    String companyName,
    String name,
    BigDecimal minPrice,
    BigDecimal maxPrice,
    Boolean isDeleted,
    LocalDateTime startCreatedAt,
    LocalDateTime endCreatedAt
) {

  public static SearchProductConditionDto of(UUID companyId, String companyName, String name,
      BigDecimal minPrice, BigDecimal maxPrice, Boolean isDeleted,
      LocalDateTime startCreatedAt, LocalDateTime endCreatedAt) {

      return SearchProductConditionDto.builder()
          .companyId(companyId)
          .companyName(companyName)
          .name(name)
          .minPrice(minPrice)
          .maxPrice(maxPrice)
          .isDeleted(isDeleted)
          .startCreatedAt(startCreatedAt)
          .endCreatedAt(endCreatedAt)
          .build();
  }

  public SearchProductCriteria toCriteria() {
    return SearchProductCriteria.builder()
        .companyId(companyId)
        .companyName(companyName)
        .name(name)
        .minPrice(minPrice)
        .maxPrice(maxPrice)
        .isDeleted(isDeleted)
        .startCreatedAt(startCreatedAt)
        .endCreatedAt(endCreatedAt)
        .build();
  }
}
