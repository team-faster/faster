package com.faster.order.app.order.application.dto.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public record GetCompanyApplicationResponseDto(
    UUID id,
    UUID hubId,
    UUID companyManagerId,
    String name,
    String contact,
    String address,
    String type
) {

}
