package com.faster.product.app.product.application.dto.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateProductHubApplicationRequestDto(
    UUID companyId,
    UUID hubId
) {

}
