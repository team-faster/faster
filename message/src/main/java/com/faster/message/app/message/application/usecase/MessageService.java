package com.faster.message.app.message.application.usecase;

import com.faster.message.app.message.application.dto.SaveGeminiMessageRequestDto;
import com.faster.message.app.message.application.dto.SaveMessageRequestDto;
import com.faster.message.app.message.presentation.dto.response.SaveMessageResponseDto;

public interface MessageService {

  SaveMessageResponseDto saveMessage(SaveMessageRequestDto requestDto);
  String saveGeminiMessage(SaveGeminiMessageRequestDto requestDto);
}
