package com.faster.message.app.message.presentation;

import com.common.response.ApiResponse;
import com.faster.message.app.global.response.MessageResponseCode;
import com.faster.message.app.message.application.dto.request.ASaveMessageRequestDto;
import com.faster.message.app.message.application.dto.response.ASaveMessageResponseDto;
import com.faster.message.app.message.application.usecase.MessageService;
import com.faster.message.app.message.presentation.dto.request.PSaveMessageRequestDto;
import com.faster.message.app.message.presentation.dto.response.PSaveMessageResponseDto;
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

  private final MessageService messageService;

  @PostMapping
  public ResponseEntity<ApiResponse<PSaveMessageResponseDto>> saveMessage(
      @RequestBody PSaveMessageRequestDto requestDto) {

    // Presentation 계층 요청 DTO -> Application 계층 요청 DTO
    ASaveMessageRequestDto aSaveMessageRequestDto = ASaveMessageRequestDto.builder()
        .deliveryId(requestDto.deliveryId())
        .orderId(requestDto.orderId())
        .orderUserName(requestDto.orderUserName())
        .orderUserSlackId(requestDto.orderUserSlackId())
        .hubSource(requestDto.hubSource())
        .hubWaypoint(requestDto.hubWaypoint())
        .hubDestination(requestDto.hubDestination())
        .deliveryManager(requestDto.deliveryManager())
        .build();

    // Service
    ASaveMessageResponseDto aSaveMessageResponseDto = messageService.saveAndSendMessageByHubManager(aSaveMessageRequestDto);

    // Application 계층 응답 DTO -> Presentation 계층 응답 DTO
    PSaveMessageResponseDto pSaveMessageResponseDto = PSaveMessageResponseDto.builder()
        .deliveryId(requestDto.deliveryId())
        .orderId(aSaveMessageResponseDto.orderId())
        .orderUserName(aSaveMessageResponseDto.orderUserName())
        .orderUserSlackId(aSaveMessageResponseDto.orderUserSlackId())
        .messageContent(aSaveMessageResponseDto.messageContent())
        .hubWaypoint(aSaveMessageResponseDto.hubWaypoint())
        .hubDestination(aSaveMessageResponseDto.hubDestination())
        .deliveryManager(aSaveMessageResponseDto.deliveryManager())
        .build();

    return ResponseEntity.ok()
        .body(new ApiResponse<>(MessageResponseCode.MESSAGE_CREATED.getMessage(),
            HttpStatus.OK.value(),
            pSaveMessageResponseDto));
  }
}
