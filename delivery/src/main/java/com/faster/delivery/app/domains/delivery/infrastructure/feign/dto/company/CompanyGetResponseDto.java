package com.faster.delivery.app.domains.delivery.infrastructure.feign.dto.company;

import java.util.UUID;
import lombok.Builder;

@Builder
public record CompanyGetResponseDto (
    UUID id,
    Long companyManagerId,
    String companyManagerName,
    String companyManagerSlackId,
    String name,
    String contact,
    String address,
    UUID hubId,
    String type
) {

}
