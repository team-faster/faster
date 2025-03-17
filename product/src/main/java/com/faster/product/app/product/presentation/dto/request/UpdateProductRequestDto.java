package com.faster.product.app.product.presentation.dto.request;

import com.faster.product.app.product.application.dto.request.UpdateProductApplicationRequestDto;
import java.math.BigDecimal;

public record UpdateProductRequestDto(
    String name,
    BigDecimal price,
    Integer quantity,
    String description
) {

  public UpdateProductApplicationRequestDto toApplicationRequestDto() {
    return UpdateProductApplicationRequestDto.builder()
        .name(name)
        .price(price)
        .quantity(quantity)
        .description(description)
        .build();
  }
}
