package com.faster.product.app.product.presentation.dto.response;

import com.faster.product.app.product.application.dto.request.GetProductsApplicationResponseDto;
import com.faster.product.app.product.application.dto.request.GetProductsApplicationResponseDto.GetProductApplicationResponseDto;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record GetProductsResponseDto(
    Map<UUID, GetProductResponseDto> products
) {

  public static GetProductsResponseDto from(GetProductsApplicationResponseDto applicationResponseDto) {
    return GetProductsResponseDto.builder()
        .products(applicationResponseDto.products()
            .stream()
            .collect(Collectors.toMap(
                GetProductApplicationResponseDto::id,
                GetProductResponseDto::from
            )))
        .build();

  }

  @Builder
  record GetProductResponseDto(
      UUID id,
      UUID hubId,
      UUID companyId,
      String companyName,
      String name,
      BigDecimal price,
      Integer quantity,
      String description
  ) {

    public static GetProductResponseDto from(GetProductApplicationResponseDto applicationResponseDto) {
      return GetProductResponseDto.builder()
          .id(applicationResponseDto.id())
          .hubId(applicationResponseDto.hubId())
          .companyId(applicationResponseDto.companyId())
          .companyName(applicationResponseDto.companyName())
          .name(applicationResponseDto.name())
          .price(applicationResponseDto.price())
          .quantity(applicationResponseDto.quantity())
          .description(applicationResponseDto.description())
          .build();
    }
  }
}
