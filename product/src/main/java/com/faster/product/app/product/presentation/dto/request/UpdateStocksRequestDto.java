package com.faster.product.app.product.presentation.dto.request;

import com.faster.product.app.product.application.dto.request.SortedUpdateStocksApplicationRequestDto;
import com.faster.product.app.product.application.dto.request.SortedUpdateStocksApplicationRequestDto.UpdateStockApplicationRequestDto;
import jakarta.validation.constraints.NotEmpty;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public record UpdateStocksRequestDto(
    @NotEmpty List<UpdateStockRequestDto> updateStockRequests
) {

  public SortedUpdateStocksApplicationRequestDto toSortedApplicationRequestDto() {
    return SortedUpdateStocksApplicationRequestDto.builder()
        .sortedUpdateStockRequests(
            updateStockRequests.stream()
                .map(UpdateStockRequestDto::toApplicationRequestDto)
                .sorted(Comparator.comparing(UpdateStockApplicationRequestDto::id))
                .toList()
        )
        .build();
  }

  public record UpdateStockRequestDto(
      UUID id,
      Integer quantity
  ) {
    public UpdateStockApplicationRequestDto toApplicationRequestDto() {
      return UpdateStockApplicationRequestDto.builder()
          .id(id)
          .quantity(quantity)
          .build();
    }
  }
}

