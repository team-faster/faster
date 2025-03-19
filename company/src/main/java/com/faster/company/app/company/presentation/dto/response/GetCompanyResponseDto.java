package com.faster.company.app.company.presentation.dto.response;

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

}
