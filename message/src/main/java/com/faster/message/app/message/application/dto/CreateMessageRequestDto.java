package com.faster.message.app.message.application.dto;

import com.faster.message.app.message.domain.enums.MessageType;
import java.time.LocalDateTime;

public record CreateMessageRequestDto(
    String targetSlackId,
    String contents,
    MessageType messageType,
    LocalDateTime sendAt
) {
}
