package com.faster.message.app.message.presentation.dto.response;

import com.faster.message.app.message.domain.entity.Message;
import com.faster.message.app.message.domain.enums.Type;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateMessageResponseDto(
    UUID id,
    String targetSlackId,
    String contents,
    LocalDateTime sendAt,
    Type type,
    LocalDateTime createdAt,
    Long createdBy) {

  public static CreateMessageResponseDto of(Message message) {
    return new CreateMessageResponseDto(
        message.getId(),
        message.getTargetSlackId(),
        message.getContents(),
        message.getSendAt(),
        message.getType(),
        message.getCreatedAt(),
        message.getCreatedBy());
  }
}
