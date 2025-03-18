package com.faster.user.app.user.application.dto.response;

import com.common.resolver.dto.UserRole;
import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.presentation.dto.response.PGetUserResponseDto;
import java.time.LocalDateTime;

public record AGetUserResponseDto(
    Long userId,
    String username,
    String slackId,
    String name,
    UserRole role,
    LocalDateTime createdAt,
    LocalDateTime deletedAt,
    Long deletedBy
) {

  public static PGetUserResponseDto from(AGetUserResponseDto responseDto) {
    return new PGetUserResponseDto(
        responseDto.userId,
        responseDto.username,
        responseDto.slackId,
        responseDto.name,
        responseDto.role,
        responseDto.createdAt,
        responseDto.deletedAt,
        responseDto.deletedBy
    );
  }

  public static AGetUserResponseDto from(User user) {
    return new AGetUserResponseDto(
        user.getId(),
        user.getUsername(),
        user.getSlackId(),
        user.getName(),
        user.getRole(),
        user.getCreatedAt(),
        user.getDeletedAt(),
        user.getDeletedBy()
    );
  }
}
