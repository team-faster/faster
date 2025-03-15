package com.faster.order.app.order.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public record OrdererInfoDetailResponseDto(
    UUID id,
    String receivingCompanyName,
    String receivingCompanyAddress,
    String receivingCompanyContact
) {

}
