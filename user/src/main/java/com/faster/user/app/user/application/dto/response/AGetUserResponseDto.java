package com.faster.user.app.user.application.dto.response;

import com.faster.user.app.user.domain.entity.User;
import java.time.LocalDateTime;

public record AGetUserResponseDto(
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

  public static AGetUserResponseDto from(User user) {
    return new AGetUserResponseDto(
        user.getId(),
        user.getUsername(),
        user.getSlackId(),
        user.getName(),
        user.getRole().toString(),
        user.getCreatedAt(),
        user.getCreatedBy(),
        user.getUpdatedAt(),
        user.getUpdatedBy(),
        user.getDeletedAt(),
        user.getDeletedBy()
    );
  }
}
