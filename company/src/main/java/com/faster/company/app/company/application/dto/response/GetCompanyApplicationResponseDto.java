package com.faster.company.app.company.application.dto.response;

import com.faster.company.app.company.domain.entity.Company;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetCompanyApplicationResponseDto(
    UUID companyId,
    UUID hubId,
    Long companyManagerId,
    String name,
    String contact,
    String address,
    String type,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public static GetCompanyApplicationResponseDto from(Company company) {
    return GetCompanyApplicationResponseDto.builder()
        .companyId(company.getId())
        .hubId(company.getHubId())
        .companyManagerId(company.getCompanyManagerId())
        .name(company.getName())
        .contact(company.getContact())
        .address(company.getAddress())
        .type(company.getType().toString())
        .createdAt(company.getCreatedAt())
        .updatedAt(company.getUpdatedAt())
        .build();
  }
}
