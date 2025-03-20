package com.faster.product.app.product.presentation.dto.response;

import com.faster.product.app.product.application.dto.response.UpdateProductHubApplicationResponseDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateProductHubResponseDto(
    UUID companyId,
    UUID hubId
) {

  public static UpdateProductHubResponseDto from(UpdateProductHubApplicationResponseDto applicationDto) {
    return UpdateProductHubResponseDto.builder()
        .companyId(applicationDto.companyId())
        .hubId(applicationDto.hubId())
        .build();
  }
}
