package com.faster.product.app.product.application.dto.request;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record UpdateStocksApplicationRequestDto(
    List<UpdateStockApplicationRequestDto> updateStockRequests
) {

  public Map<UUID, Integer> toUpdateStockMap() {
    return this.updateStockRequests().stream()
        .collect(Collectors.toMap(
            UpdateStockApplicationRequestDto::id,
            UpdateStockApplicationRequestDto::quantity,
            Integer::sum
        ));
  }

  @Builder
  public record UpdateStockApplicationRequestDto(
      UUID id,
      Integer quantity
  ) {
  }
}
