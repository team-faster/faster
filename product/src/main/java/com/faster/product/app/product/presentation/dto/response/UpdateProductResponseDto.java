package com.faster.product.app.product.presentation.dto.response;

import com.faster.product.app.product.application.dto.response.UpdateProductApplicationResponseDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateProductResponseDto(
    UUID id,
    UUID hubId,
    UUID companyId,
    String companyName,
    String name,
    BigDecimal price,
    Integer quantity,
    String description,
    LocalDateTime createdAt
) {

  public static UpdateProductResponseDto from(
      UpdateProductApplicationResponseDto applicationResponseDto) {

    return UpdateProductResponseDto.builder()
        .id(applicationResponseDto.id())
        .hubId(applicationResponseDto.hubId())
        .companyId(applicationResponseDto.companyId())
        .companyName(applicationResponseDto.companyName())
        .name(applicationResponseDto.name())
        .price(applicationResponseDto.price())
        .quantity(applicationResponseDto.quantity())
        .description(applicationResponseDto.description())
        .createdAt(applicationResponseDto.createdAt())
        .build();
  }
}
