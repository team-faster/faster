package com.faster.company.app.company.presentation.dto.response;

import com.faster.company.app.company.domain.enums.CompanyType;
import java.time.LocalDateTime;
import java.util.UUID;

public record SaveCompanyResponseDto(
    UUID id,
    String name,
    String companyManagerId,
    String address,
    String hubId,
    CompanyType companyType,
    LocalDateTime createdAt,
    Long createdBy
) {

  public static SaveCompanyResponseDto of(UUID id, String name, String companyManagerId, String address, String hubId,
                                            CompanyType companyType, LocalDateTime createdAt, Long createdBy) {
    return new SaveCompanyResponseDto(
        id,
        name,
        companyManagerId,
        address,
        hubId,
        companyType,
        createdAt,
        createdBy
    );
  }
}
