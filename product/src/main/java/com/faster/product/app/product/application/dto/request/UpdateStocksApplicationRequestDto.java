package com.faster.product.app.product.application.dto.request;

import java.util.List;
import java.util.Map;
import java.util.Set;
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

  public Set<UUID> getProductIdsSet() {
    return this.updateStockRequests().stream()
        .map(UpdateStockApplicationRequestDto::id).collect(Collectors.toSet());
  }

  @Builder
  public record UpdateStockApplicationRequestDto(
      UUID id,
      Integer quantity
  ) {
  }
}
