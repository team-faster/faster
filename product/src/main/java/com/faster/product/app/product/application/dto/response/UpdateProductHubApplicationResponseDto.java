package com.faster.product.app.product.application.dto.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateProductHubApplicationResponseDto(
    UUID companyId,
    UUID hubId
) {

  public static UpdateProductHubApplicationResponseDto of(UUID companyId, UUID hubId) {
    return UpdateProductHubApplicationResponseDto.builder()
        .companyId(companyId)
        .hubId(hubId)
        .build();
  }
}
