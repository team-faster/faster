package com.faster.product.app.product.presentation.dto.response;

import com.faster.product.app.product.application.dto.response.SearchProductApplicationResponseDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchProductResponseDto(
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

  public static SearchProductResponseDto from(
      SearchProductApplicationResponseDto productDto) {
    return com.faster.product.app.product.presentation.dto.response.SearchProductResponseDto.builder()
        .id(productDto.id())
        .hubId(productDto.hubId())
        .companyId(productDto.companyId())
        .companyName(productDto.companyName())
        .name(productDto.name())
        .price(productDto.price())
        .quantity(productDto.quantity())
        .description(productDto.description())
        .createdAt(productDto.createdAt())
        .updatedAt(productDto.updatedAt())
        .build();
  }
}
