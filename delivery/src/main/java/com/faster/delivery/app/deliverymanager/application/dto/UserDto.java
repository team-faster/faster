package com.faster.delivery.app.deliverymanager.application.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UserDto(
    Long userId,
    String username,
    String slackId,
    String name,
    String role,

    Long createdBy,
    LocalDateTime createdAt,
    Long updatedBy,
    LocalDateTime updatedAt,
    Long deletedBy,
    LocalDateTime deletedAt
) {
}
