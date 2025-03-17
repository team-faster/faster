package com.faster.delivery.app.deliverymanager.application.dto;

import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.UserGetResponseDto;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UserDto(
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

  public static UserDto from(UserGetResponseDto responseDto) {
    return UserDto.builder()
        .userId(responseDto.userId())
        .username(responseDto.username())
        .slackId(responseDto.slackId())
        .name(responseDto.name())
        .role(responseDto.role())
        .createdBy(responseDto.createdBy())
        .createdAt(responseDto.createdAt())
        .updatedBy(responseDto.updatedBy())
        .updatedAt(responseDto.updatedAt())
        .deletedBy(responseDto.deletedBy())
        .deletedAt(responseDto.deletedAt())
        .build();
  }
}
