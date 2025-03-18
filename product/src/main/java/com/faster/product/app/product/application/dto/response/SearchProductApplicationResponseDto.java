package com.faster.product.app.product.application.dto.response;

import com.faster.product.app.product.domain.entity.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchProductApplicationResponseDto(
    UUID id,
    UUID hubId,
    UUID companyId,
    String companyName,
    String name,
    BigDecimal price,
    Integer quantity,
    String description,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public static SearchProductApplicationResponseDto from(Product product) {
    return SearchProductApplicationResponseDto.builder()
        .id(product.getId())
        .hubId(product.getHubId())
        .companyId(product.getCompanyId())
        .companyName(product.getCompanyName())
        .name(product.getName())
        .price(product.getPrice())
        .quantity(product.getQuantity())
        .description(product.getDescription())
        .createdAt(product.getCreatedAt())
        .updatedAt(product.getUpdatedAt())
        .build();
  }
}
