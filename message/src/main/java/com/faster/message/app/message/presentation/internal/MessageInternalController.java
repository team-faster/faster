package com.faster.message.app.message.presentation.internal;

import com.common.response.ApiResponse;
import com.faster.message.app.global.response.MessageResponseCode;
import com.faster.message.app.message.application.dto.CreateGeminiMessageRequestDto;
import com.faster.message.app.message.application.usecase.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/internal/messages")
@RestController
public class MessageInternalController {

  private final MessageService messageUseCase;


  @PostMapping("/gemini")
  public ResponseEntity<ApiResponse<String>> createGeminiMessage(
      @RequestBody CreateGeminiMessageRequestDto requestDto) {
    String geminiMessage = messageUseCase.createGeminiMessage(requestDto);

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            MessageResponseCode.MESSAGE_CREATED.getMessage(),
            HttpStatus.OK.value(),
            geminiMessage));
  }
}
