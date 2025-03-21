package com.faster.user.app.user.presentation.dto.response;

import com.common.resolver.dto.UserRole;
import com.faster.user.app.user.application.dto.response.AGetUserResponseDto;
import java.time.LocalDateTime;

public record PGetUserResponseDto(
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
  public static PGetUserResponseDto from(AGetUserResponseDto responseDto) {
    return new PGetUserResponseDto(
        responseDto.userId(),
        responseDto.username(),
        responseDto.slackId(),
        responseDto.name(),
        responseDto.role(),
        responseDto.createdAt(),
        responseDto.createdBy(),
        responseDto.updatedAt(),
        responseDto.updatedBy(),
        responseDto.deletedAt(),
        responseDto.deletedBy()
    );
  }
}
