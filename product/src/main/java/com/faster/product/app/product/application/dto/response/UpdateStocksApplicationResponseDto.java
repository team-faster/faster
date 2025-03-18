package com.faster.product.app.product.application.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateStocksApplicationResponseDto(
  List<UpdateStockApplicationResponseDto> updateStockApplicationResponses
) {

  public static UpdateStocksApplicationResponseDto from(
      List<UpdateStockApplicationResponseDto> applicationResponses) {
    return UpdateStocksApplicationResponseDto.builder()
        .updateStockApplicationResponses(applicationResponses)
        .build();
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
