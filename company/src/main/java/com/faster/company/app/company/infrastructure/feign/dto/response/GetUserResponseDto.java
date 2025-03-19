package com.faster.company.app.company.infrastructure.feign.dto.response;

import com.faster.company.app.company.application.dto.response.GetUserApplicationResponseDto;
import java.time.LocalDateTime;

public record GetUserResponseDto(
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

  public GetUserApplicationResponseDto toApplicationDto() {

    return GetUserApplicationResponseDto.builder()
        .userId(userId)
        .username(username)
        .slackId(slackId)
        .name(name)
        .role(role)
        .createdAt(createdAt)
        .createdBy(createdBy)
        .updatedAt(updatedAt)
        .updatedBy(updatedBy)
        .deletedAt(deletedAt)
        .deletedBy(deletedBy)
        .build();
  }
}