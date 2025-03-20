package com.faster.company.app.company.application.dto.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateProductHubApplicationResponseDto(
    UUID companyId,
    UUID hubId
) {

}
