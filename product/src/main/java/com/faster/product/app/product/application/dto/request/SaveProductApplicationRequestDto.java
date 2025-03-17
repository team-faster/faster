package com.faster.product.app.product.application.dto.request;

import com.faster.product.app.product.domain.entity.Product;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SaveProductApplicationRequestDto(
    UUID hubId,
    UUID companyId,
    String companyName,
    String name,
    BigDecimal price,
    Integer quantity,
    String description
) {

  public Product toEntity() {
    return Product.of(hubId, companyId, companyName, name, price, quantity, description);
  }
}
