package com.faster.company.app.company.presentation.dto.response;

import com.faster.company.app.company.application.dto.response.UpdateCompanyApplicationResponseDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateCompanyResponseDto(
    UUID companyId
) {

  public static UpdateCompanyResponseDto from(UpdateCompanyApplicationResponseDto companyDto) {

    return UpdateCompanyResponseDto.builder()
        .companyId(companyDto.companyId())
        .build();
  }
}
