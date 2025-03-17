package com.faster.product.app.product.application.dto.request;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record UpdateProductApplicationRequestDto(
    String name,
    BigDecimal price,
    Integer quantity,
    String description
) {

}
