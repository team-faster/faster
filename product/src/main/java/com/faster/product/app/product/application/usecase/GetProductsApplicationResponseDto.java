package com.faster.product.app.product.application.usecase;

import com.faster.product.app.product.domain.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetProductsApplicationResponseDto(
    List<GetProductApplicationResponseDto> products
) {

  public static GetProductsApplicationResponseDto from(List<Product> products) {
    return GetProductsApplicationResponseDto.builder()
        .products(products.stream()
            .map(GetProductApplicationResponseDto::from)
            .toList())
        .build();
  }

  @Builder
  public record GetProductApplicationResponseDto(
      UUID id,
      UUID hubId,
      UUID companyId,
      String companyName,
      String name,
      BigDecimal price,
      Integer quantity,
      String description
  ) {

    public static GetProductApplicationResponseDto from(Product product) {
      return GetProductApplicationResponseDto.builder()
          .id(product.getId())
          .hubId(product.getHubId())
          .companyId(product.getCompanyId())
          .companyName(product.getCompanyName())
          .name(product.getName())
          .price(product.getPrice())
          .quantity(product.getQuantity())
          .description(product.getDescription())
          .build();
    }
  }
}
