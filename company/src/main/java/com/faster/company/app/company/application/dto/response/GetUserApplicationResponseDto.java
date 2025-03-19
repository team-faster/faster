package com.faster.company.app.company.application.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GetUserApplicationResponseDto(
    Long userId,
    String username,
    String slackId,
    String name,
    String role,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy,
    LocalDateTime deletedAt,
    Long deletedBy
) {

}
