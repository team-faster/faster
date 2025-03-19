package com.faster.company.app.company.application.dto.response;

import com.faster.company.app.company.domain.entity.Company;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetCompanyApplicationResponseDto(
    UUID companyId,
    UUID hubId,
    Long companyManagerUserId,
    String name,
    String contact,
    String address,
    String type,
    String companyManagerSlackId,
    String companyManagerName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public static GetCompanyApplicationResponseDto from(
      Company company, GetUserApplicationResponseDto userDto) {

    return GetCompanyApplicationResponseDto.builder()
        .companyId(company.getId())
        .hubId(company.getHubId())
        .companyManagerUserId(company.getCompanyManagerId())
        .name(company.getName())
        .contact(company.getContact())
        .address(company.getAddress())
        .type(company.getType().toString())
        .companyManagerSlackId(userDto.slackId())
        .companyManagerName(userDto.name())
        .createdAt(company.getCreatedAt())
        .updatedAt(company.getUpdatedAt())
        .build();
  }
}
