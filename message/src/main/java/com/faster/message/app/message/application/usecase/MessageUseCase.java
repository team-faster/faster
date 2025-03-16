package com.faster.message.app.message.application.usecase;

import com.faster.message.app.message.application.dto.CreateMessageRequestDto;
import com.faster.message.app.message.presentation.dto.response.CreateMessageResponseDto;

public interface MessageUseCase {

  public CreateMessageResponseDto createMessage(CreateMessageRequestDto requestDto);
}
