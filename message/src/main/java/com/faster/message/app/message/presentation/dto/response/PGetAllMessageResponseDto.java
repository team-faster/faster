package com.faster.message.app.message.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PGetAllMessageResponseDto(
    UUID id,
    String targetSlackId,
    String content,
    String messageType,
    LocalDateTime sendAt
) {
}
