package com.faster.company.app.company.presentation.dto.response;

import com.faster.company.app.company.application.dto.response.GetCompanyApplicationResponseDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetCompanyResponseDto(
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

  public static GetCompanyResponseDto from(GetCompanyApplicationResponseDto companyDto) {

    return GetCompanyResponseDto.builder()
        .companyId(companyDto.companyId())
        .hubId(companyDto.hubId())
        .companyManagerId(companyDto.companyManagerId())
        .name(companyDto.name())
        .contact(companyDto.contact())
        .address(companyDto.address())
        .type(companyDto.type())
        .createdAt(companyDto.createdAt())
        .updatedAt(companyDto.updatedAt())
        .build();
  }
}
