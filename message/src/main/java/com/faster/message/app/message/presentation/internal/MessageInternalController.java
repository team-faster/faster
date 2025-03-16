package com.faster.message.app.message.presentation.internal;

import static com.faster.message.app.global.response.MessageResponseCode.*;

import com.common.response.ApiResponse;
import com.faster.message.app.global.response.MessageResponseCode;
import com.faster.message.app.message.application.dto.CreateMessageRequestDto;
import com.faster.message.app.message.application.usecase.MessageUseCase;
import com.faster.message.app.message.presentation.dto.response.CreateMessageResponseDto;
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

  private final MessageUseCase messageUseCase;

  @PostMapping
  public ResponseEntity<ApiResponse<CreateMessageResponseDto>> createMessage(
      @RequestBody CreateMessageRequestDto responseDto) {
    CreateMessageResponseDto message = messageUseCase.createMessage(responseDto);
    return ResponseEntity
        .status(MESSAGE_CREATED.getStatus())
        .body(new ApiResponse<>(
            MESSAGE_CREATED.getMessage(),
            MESSAGE_CREATED.getStatus().value(),
            message
        ));
  }
}
