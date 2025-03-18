package com.faster.order.app.order.application.dto.response;

import com.faster.order.app.order.domain.entity.OrderItem;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateStocksApplicationResponseDto(
  List<UpdateStockApplicationResponseDto> updateStockApplicationResponses
) {


  @Builder
  public record UpdateStockApplicationResponseDto(
      UUID id,
      UpdateStatus status
  ) {
  }

  public enum UpdateStatus {
    SUCCESS, FAIL
  }
}
