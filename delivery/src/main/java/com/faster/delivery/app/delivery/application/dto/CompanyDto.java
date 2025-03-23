package com.faster.delivery.app.delivery.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record CompanyDto(
    UUID companyId,
    Long companyManagerUserId,
    String companyManagerName,
    String companyManagerSlackId,
    String name,
    String contact,
    String address,
    UUID hubId,
    String type
) {

}
