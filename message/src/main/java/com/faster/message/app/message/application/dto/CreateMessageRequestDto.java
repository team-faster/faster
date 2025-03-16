package com.faster.message.app.message.application.dto;

import com.faster.message.app.message.domain.enums.Type;
import java.time.LocalDateTime;

public record CreateMessageRequestDto(
    String targetSlackId,
    String contents,
    Type type,
    LocalDateTime sendAt
) {
}
