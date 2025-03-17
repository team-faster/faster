package com.faster.message.app.message.presentation.dto.response;

import com.faster.message.app.message.domain.entity.Message;
import com.faster.message.app.message.domain.enums.MessageType;
import java.time.LocalDateTime;
import java.util.UUID;

public record SaveMessageResponseDto(
    UUID id,
    String targetSlackId,
    String contents,
    LocalDateTime sendAt,
    MessageType messageType,
    LocalDateTime createdAt,
    Long createdBy) {

  public static SaveMessageResponseDto from(Message message) {
    return new SaveMessageResponseDto(
        message.getId(),
        message.getTargetSlackId(),
        message.getContents(),
        message.getSendAt(),
        message.getMessageType(),
        message.getCreatedAt(),
        message.getCreatedBy());
  }
}
