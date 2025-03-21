package com.faster.delivery.app.delivery.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record SendMessageApplicationRequestDto (
    UUID deliveryId,
    UUID orderId,
    String orderUserName,
    String orderUserSlackId,
    String hubSource,
    String hubWaypoint,
    String hubDestination,

    String deliveryManager
//    String deliveryManagerSlackId
) {
}
