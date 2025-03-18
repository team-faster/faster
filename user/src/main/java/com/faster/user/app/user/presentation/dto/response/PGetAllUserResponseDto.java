package com.faster.user.app.user.presentation.dto.response;

import com.common.resolver.dto.UserRole;
import com.faster.user.app.user.application.dto.response.AGetAllUserResponseDto;
import java.time.LocalDateTime;


public record PGetAllUserResponseDto(
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
  public static PGetAllUserResponseDto from(AGetAllUserResponseDto applicationDto) {
    return new PGetAllUserResponseDto(
        applicationDto.id(),
        applicationDto.username(),
        applicationDto.name(),
        applicationDto.slackId(),
        applicationDto.role(),
        applicationDto.createdAt(),
        applicationDto.createdBy(),
        applicationDto.updatedAt(),
        applicationDto.updatedBy(),
        applicationDto.deletedAt(),
        applicationDto.deletedBy()
    );
  }
}
