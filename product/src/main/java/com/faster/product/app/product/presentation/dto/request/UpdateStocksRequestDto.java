package com.faster.product.app.product.presentation.dto.request;

import com.faster.product.app.product.application.dto.request.UpdateStocksApplicationRequestDto;
import com.faster.product.app.product.application.dto.request.UpdateStocksApplicationRequestDto.UpdateStockApplicationRequestDto;
import java.util.List;
import java.util.UUID;

public record UpdateStocksRequestDto(
    List<UpdateStockRequestDto> updateStockRequests
) {

  public UpdateStocksApplicationRequestDto toApplicationRequestDto() {
    return UpdateStocksApplicationRequestDto.builder()
        .updateStockRequests(
            updateStockRequests.stream()
                .map(UpdateStockRequestDto::toApplicationRequestDto)
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

