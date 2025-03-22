package com.faster.message.app.message.infrastructure.persistence.jpa;

import com.faster.message.app.message.presentation.dto.response.PGetAllMessageResponseDto;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface MessageRepositoryCustom {
  Page<PGetAllMessageResponseDto> searchMessages(UUID targetSlackId, String content, String messageType, LocalDate sendAt, int page, int size);
}
