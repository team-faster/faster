package com.faster.company.app.company.domain.projection;

import com.faster.company.app.company.domain.enums.CompanyType;
import java.time.LocalDateTime;
import java.util.UUID;

public record SearchCompaniesProjection(
    UUID id,
    UUID hubId,
    Long managerId,
    String name,
    String contact,
    String address,
    CompanyType type,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
