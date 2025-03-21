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
        .hubSourceName(requestDto.hubSourceName())
        .hubWaypointName(requestDto.hubWaypointName())
        .hubDestinationName(requestDto.hubDestinationName())

        .orderInfo(ASaveMessageRequestDto.OrderInfo.builder()
            .orderId(requestDto.orderInfo().orderId())
            .orderUserName(requestDto.orderInfo().orderUserName())
            .orderUserSlackId(requestDto.orderInfo().orderUserSlackId())
            .build())

        .deliveryManagers(
            requestDto.deliveryManagers().stream()
                .map(manager -> ASaveMessageRequestDto.DeliveryManagerInfo.builder()
                    .deliveryManagerId(manager.deliveryManagerId())
                    .deliveryManagerUserId(manager.deliveryManagerUserId())
                    .deliveryManagerType(manager.deliveryManagerType())
                    .deliveryManagerName(manager.deliveryManagerName())
                    .build()
                )
                .toList()
        ).build();

    // Service 호출
    ASaveMessageResponseDto aSaveMessageResponseDto = messageService.saveAndSendMessageByHubManager(
        aSaveMessageRequestDto);

    // Application 계층 응답 DTO -> Presentation 계층 응답 DTO
    PSaveMessageResponseDto pSaveMessageResponseDto = PSaveMessageResponseDto.builder()
        .deliveryId(aSaveMessageResponseDto.deliveryId())
        .orderId(aSaveMessageResponseDto.orderId())
        .orderUserName(aSaveMessageResponseDto.orderUserName())
        .orderUserSlackId(aSaveMessageResponseDto.orderUserSlackId())
        .messageContent(aSaveMessageResponseDto.messageContent())
        .hubWaypointName(aSaveMessageResponseDto.hubWaypoint())
        .hubDestinationName(aSaveMessageResponseDto.hubDestination())
        .deliveryManagerName(aSaveMessageResponseDto.deliveryManager())
        .build();

    return ResponseEntity.ok()
        .body(new ApiResponse<>(MessageResponseCode.MESSAGE_CREATED.getMessage(),
            HttpStatus.OK.value(),
            pSaveMessageResponseDto));
  }
}
