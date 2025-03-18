package com.faster.product.app.product.application.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateStocksApplicationResponseDto(
  List<UpdateStockApplicationResponseDto> updateStockApplicationResponses
) {

  public static UpdateStocksApplicationResponseDto newInstance() {

    return UpdateStocksApplicationResponseDto.builder()
        .updateStockApplicationResponses(new ArrayList<>())
        .build();
  }

  public void add(UpdateStockApplicationResponseDto dto) {
    this.updateStockApplicationResponses.add(dto);
  }

  @Builder
  public record UpdateStockApplicationResponseDto(
      UUID id,
      UpdateStatus status
  ) {

    public static UpdateStockApplicationResponseDto of(UUID productId, boolean result) {
      return UpdateStockApplicationResponseDto.builder()
          .id(productId)
          .status(result ? UpdateStatus.SUCCESS : UpdateStatus.FAIL)
          .build();
    }
  }

  public enum UpdateStatus {
    SUCCESS, FAIL
  }
}
