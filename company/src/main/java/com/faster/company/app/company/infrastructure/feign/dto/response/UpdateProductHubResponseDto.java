package com.faster.company.app.company.infrastructure.feign.dto.response;

import com.faster.company.app.company.application.dto.response.UpdateProductHubApplicationResponseDto;
import java.util.UUID;

public record UpdateProductHubResponseDto(
    UUID companyId,
    UUID hubId
) {

  public UpdateProductHubApplicationResponseDto toApplicationDto() {
    return UpdateProductHubApplicationResponseDto.builder()
        .companyId(companyId)
        .hubId(hubId)
        .build();
  }
}
