package com.faster.delivery.app.domains.delivery.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliverySaveDto(UUID orderId,
                              UUID sourceHubId,
                              UUID destinationHubId,
                              UUID receiveCompanyId) {

}
