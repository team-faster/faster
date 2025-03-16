package com.faster.message.app.message.application.usecase;

import static com.faster.message.app.global.enums.MessageErrorCode.*;

import com.faster.message.app.global.MessageInvalidBySendAtException;
import com.faster.message.app.message.application.dto.CreateMessageRequestDto;
import com.faster.message.app.message.domain.entity.Message;
import com.faster.message.app.message.domain.repository.MessageRepository;
import com.faster.message.app.message.presentation.dto.response.CreateMessageResponseDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MessageService implements MessageUseCase {

  private final MessageRepository messageRepository;

  private void checkDateTimeBySendAt(LocalDateTime sendAt) {
    if (sendAt.isBefore(LocalDateTime.now())) {
      throw new MessageInvalidBySendAtException(MESSAGE_INVALID_SEND_AT.getMessage());
    }
  }

  private void checkSlackId(String targetSlackId) {

  }

  @Transactional
  @Override
  public CreateMessageResponseDto createMessage(CreateMessageRequestDto requestDto) {
    // 1. 보내는 시간이 올바른지 확인하기
    checkDateTimeBySendAt(requestDto.sendAt());

    // 2. TODO: 슬랙 아이디가 존재하는지 확인하기
    checkSlackId(requestDto.targetSlackId());
    // 3. TODO: 올바른 타입인지 확인하기
    // 4. TODO: CREATED BY 추가하기

    Message message = Message.of(
        requestDto.targetSlackId(),
        requestDto.contents(),
        requestDto.type(),
        requestDto.sendAt());

    Message savedMessage = messageRepository.save(message);

    return CreateMessageResponseDto.of(savedMessage);
  }
}

