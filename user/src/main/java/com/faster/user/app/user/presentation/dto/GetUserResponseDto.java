package com.faster.user.app.user.presentation.dto;

import com.common.resolver.dto.UserRole;
import com.faster.user.app.user.domain.entity.User;
import java.time.LocalDateTime;

public record GetUserResponseDto(
    Long userId,
    String username,
    String slackId,
    String name,
    UserRole role,
    LocalDateTime createdAt,
    Long modifiedBy,
    LocalDateTime deletedAt,
    Long deletedBy
) {

  public static GetUserResponseDto from(User user) {
    return new GetUserResponseDto(
        user.getId(),
        user.getUsername(),
        user.getSlackId(),
        user.getName(),
        user.getRole(),
        user.getCreatedAt(),
        user.getCreatedBy(),
        user.getDeletedAt(),
        user.getDeletedBy()
    );
  }
}
