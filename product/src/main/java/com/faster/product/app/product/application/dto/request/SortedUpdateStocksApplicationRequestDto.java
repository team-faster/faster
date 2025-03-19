package com.faster.product.app.product.application.dto.request;

import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SortedUpdateStocksApplicationRequestDto(
    List<UpdateStockApplicationRequestDto> sortedUpdateStockRequests
) {

  @Builder
  public record UpdateStockApplicationRequestDto(
      UUID id,
      Integer quantity
  ) {
  }
}
