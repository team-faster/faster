package com.faster.delivery.app.delivery.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliverySaveApplicationDto(
    UUID orderId,
    UUID supplierCompanyId,
    UUID receiveCompanyId
) { }
