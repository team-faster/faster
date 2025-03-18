package com.faster.product.app.product.presentation.dto.response;

import com.faster.product.app.product.application.dto.response.UpdateStocksApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateStocksApplicationResponseDto.UpdateStockApplicationResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateStocksResponseDto(
    List<UpdateStockResponseDto> updateProductStockResponses
) {

  public static UpdateStocksResponseDto from(
      UpdateStocksApplicationResponseDto applicationResponseDto) {

    return UpdateStocksResponseDto.builder()
        .updateProductStockResponses(
            applicationResponseDto.updateStockApplicationResponses()
                .stream()
                .map(UpdateStockResponseDto::from)
                .toList()
        )
        .build();
  }

  @Builder
  record UpdateStockResponseDto(
      UUID id,
      UpdateStatus status
  ) {

    public static UpdateStockResponseDto from(
        UpdateStockApplicationResponseDto applicationResponseDto) {

      return UpdateStockResponseDto.builder()
          .id(applicationResponseDto.id())
          .status(UpdateStatus.valueOf(applicationResponseDto.status().toString()))
          .build();
    }
  }

  enum UpdateStatus {
    SUCCESS, FAIL
  }
}
