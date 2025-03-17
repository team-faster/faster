package com.faster.product.app.product.presentation.dto.request;

import com.faster.product.app.product.application.dto.request.SaveProductApplicationRequestDto;
import java.math.BigDecimal;
import java.util.UUID;

public record SaveProductRequestDto(
    UUID hubId,
    UUID companyId,
    String companyName,
    String name,
    BigDecimal price,
    Integer quantity,
    String description
) {

  public SaveProductApplicationRequestDto toApplicationRequestDto() {
    return SaveProductApplicationRequestDto.builder()
        .hubId(hubId)
        .companyId(companyId)
        .companyName(companyName)
        .name(name)
        .price(price)
        .quantity(quantity)
        .description(description)
        .build();
  }
}
