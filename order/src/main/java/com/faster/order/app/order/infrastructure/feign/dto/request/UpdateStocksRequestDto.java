package com.faster.order.app.order.infrastructure.feign.dto.request;

import com.faster.order.app.order.application.dto.request.UpdateStocksApplicationRequestDto;
import com.faster.order.app.order.application.dto.request.UpdateStocksApplicationRequestDto.UpdateStockApplicationRequestDto;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateStocksRequestDto(
    List<UpdateStockRequestDto> updateStockRequests
) {

  public static UpdateStocksRequestDto from(UpdateStocksApplicationRequestDto requests) {

    return UpdateStocksRequestDto.builder()
        .updateStockRequests(requests.updateStockRequests()
            .stream()
            .map(UpdateStockRequestDto::from)
            .toList())
        .build();
  }

  @Builder
  public record UpdateStockRequestDto(
      UUID id,
      Integer quantity
  ) {
    public static UpdateStockRequestDto from(UpdateStockApplicationRequestDto request) {
      return UpdateStockRequestDto.builder()
          .id(request.id())
          .quantity(request.quantity())
          .build();
    }
  }
}

