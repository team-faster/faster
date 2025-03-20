package com.faster.company.app.company.application.dto.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateCompanyApplicationResponseDto(
    UUID companyId
) {

  public static UpdateCompanyApplicationResponseDto of(UUID companyId) {
    return UpdateCompanyApplicationResponseDto.builder()
        .companyId(companyId)
        .build();
  }
}
