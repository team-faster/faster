package com.faster.order.app.order.application.dto.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public record OrdererInfoDetailApplicationResponseDto(
    UUID id,
    String receivingCompanyName,
    String receivingCompanyAddress,
    String receivingCompanyContact
) {

}
