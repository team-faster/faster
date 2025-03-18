package com.faster.order.app.order.infrastructure.feign.dto.response;

import com.faster.order.app.order.application.dto.response.UpdateStocksApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.UpdateStocksApplicationResponseDto.UpdateStockApplicationResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateStocksResponseDto(
    List<UpdateStockResponseDto> updateProductStockResponses
) {


  public UpdateStocksApplicationResponseDto toApplicationDto() {
    return UpdateStocksApplicationResponseDto.builder()
        .updateStockApplicationResponses(
            updateProductStockResponses.stream()
                .map(UpdateStockResponseDto::toApplicationDto)
                .toList()
        )
        .build();
  }

  @Builder
  record UpdateStockResponseDto(
      UUID id,
      UpdateStatus status
  ) {

    public UpdateStockApplicationResponseDto toApplicationDto() {
      return UpdateStockApplicationResponseDto.builder()
          .id(id)
          .status(UpdateStocksApplicationResponseDto.UpdateStatus.valueOf(status.toString()))
          .build();
    }
  }

  enum UpdateStatus {
    SUCCESS, FAIL
  }
}
