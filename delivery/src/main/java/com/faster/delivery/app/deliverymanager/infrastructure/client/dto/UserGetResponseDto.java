package com.faster.delivery.app.deliverymanager.infrastructure.client.dto;

import com.faster.delivery.app.deliverymanager.application.dto.UserDto;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UserGetResponseDto(
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
  public UserDto toUserDto() {
    return UserDto.builder()
        .userId(this.userId())
        .username(this.username())
        .slackId(this.slackId())
        .name(this.name())
        .role(this.role())
        .createdBy(this.createdBy())
        .createdAt(this.createdAt())
        .updatedBy(this.updatedBy())
        .updatedAt(this.updatedAt())
        .deletedBy(this.deletedBy())
        .deletedAt(this.deletedAt())
        .build();
  }
}
