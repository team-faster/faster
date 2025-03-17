package com.faster.user.app.user.presentation.dto;

import com.common.resolver.dto.UserRole;
import java.time.LocalDateTime;


public record GetAllUserResponseDto(
    Long id,
    String username,
    String name,
    String slackId,
    UserRole role,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy,
    LocalDateTime deletedAt,
    Long deletedBy
) {

}
