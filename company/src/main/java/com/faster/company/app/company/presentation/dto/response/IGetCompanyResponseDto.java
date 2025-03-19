package com.faster.company.app.company.presentation.dto.response;

import com.faster.company.app.company.application.dto.response.IGetCompanyApplicationResponseDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record IGetCompanyResponseDto(
    UUID companyId,
    UUID hubId,
    Long companyManagerUserId,
    String name,
    String contact,
    String address,
    String type,
    String companyMangerSlackId,
    String companyManagerName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public static IGetCompanyResponseDto from(IGetCompanyApplicationResponseDto responseDto) {
    return IGetCompanyResponseDto.builder()
        .companyId(responseDto.companyId())
        .hubId(responseDto.hubId())
        .companyManagerUserId(responseDto.companyManagerUserId())
        .name(responseDto.name())
        .contact(responseDto.contact())
        .address(responseDto.address())
        .type(responseDto.type())
        .companyMangerSlackId(responseDto.companyManagerSlackId())
        .companyManagerName(responseDto.companyManagerName())
        .createdAt(responseDto.createdAt())
        .updatedAt(responseDto.updatedAt())
        .build();
  }
}
