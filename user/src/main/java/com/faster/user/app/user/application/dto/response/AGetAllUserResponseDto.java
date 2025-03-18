package com.faster.user.app.user.application.dto.response;

import com.common.resolver.dto.UserRole;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserProjection;
import java.time.LocalDateTime;


public record AGetAllUserResponseDto(
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
  public static AGetAllUserResponseDto from(QUserProjection projection) {
    return new AGetAllUserResponseDto(
        projection.getId(),
        projection.getUsername(),
        projection.getName(),
        projection.getSlackId(),
        projection.getRole(),
        projection.getCreatedAt(),
        projection.getCreatedBy(),
        projection.getUpdatedAt(),
        projection.getUpdatedBy(),
        projection.getDeletedAt(),
        projection.getDeletedBy()
    );
  }

}
