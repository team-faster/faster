package com.faster.message.app.message.presentation;

import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.message.app.global.response.MessageResponseCode;
import com.faster.message.app.message.application.client.HubClient;
import com.faster.message.app.message.application.client.OrderClient;
import com.faster.message.app.message.application.client.UserClient;
import com.faster.message.app.message.application.dto.request.ASaveMessageRequestDto;
import com.faster.message.app.message.application.dto.response.AGetHubResponseDto;
import com.faster.message.app.message.application.dto.response.AGetOrderResponseDto;
import com.faster.message.app.message.application.dto.response.AGetUserResponseDto;
import com.faster.message.app.message.application.dto.response.ASaveMessageResponseDto;
import com.faster.message.app.message.application.usecase.MessageService;
import com.faster.message.app.message.application.usecase.MessageServiceImpl;
import com.faster.message.app.message.presentation.dto.request.PSaveMessageRequestDto;
import com.faster.message.app.message.presentation.dto.response.PGetAllMessageResponseDto;
import com.faster.message.app.message.presentation.dto.response.PSaveMessageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메시지(Internal)", description = "메시지 생성 및 조회")
@RequiredArgsConstructor
@RequestMapping("/internal/messages")
@RestController
public class MessageInternalController {

  private final MessageService messageService;

  private final MessageServiceImpl messageServiceImpl;


  private final OrderClient orderClient;
  private final HubClient hubClient;
  private final UserClient userClient;

  @Operation(summary = "메시지 저장", description = "메시지 저장 API 입니다.")
  @PostMapping
  public ResponseEntity<ApiResponse<PSaveMessageResponseDto>> saveMessage(
      @RequestBody PSaveMessageRequestDto requestDto) {

    // Presentation 계층 요청 DTO -> Application 계층 요청 DTO
    ASaveMessageRequestDto aSaveMessageRequestDto = ASaveMessageRequestDto.builder()
        .deliveryId(requestDto.deliveryId())
        .sourceHubId(requestDto.hubSourceId())
        .receiveHubId(requestDto.receiveHubId())
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
        .hubWaypointName(aSaveMessageResponseDto.hubWaypointName())
        .hubDestinationName(aSaveMessageResponseDto.hubDestinationName())
        .deliveryManagerName(aSaveMessageResponseDto.deliveryManager())
        .build();

    return ResponseEntity.ok()
        .body(new ApiResponse<>(MessageResponseCode.MESSAGE_CREATED.getMessage(),
            HttpStatus.OK.value(),
            pSaveMessageResponseDto));
  }

  @Operation(summary = "슬랙 전송 ECHO", description = "슬랙 전송 ECHO API 입니다.")
  @PostMapping("/practice")
  public String testSlackMessage(
      @RequestParam String email,
      @RequestParam String message
  ) {

    try {
      // 1. 이메일을 이용해서 Slack User ID 찾기
      String slackUserId = messageServiceImpl.getUserIdByEmail(email);

      // 2. Slack DM 채널 열기
      String channelId = messageServiceImpl.openConversation(slackUserId);

      // 3. Slack 메시지 보내기
      String response = messageServiceImpl.sendMessage(channelId, message);

      return "Slack 메시지 전송 성공: " + response;
    } catch (Exception e) {
      return "Slack 메시지 전송 오류: " + e.getMessage();
    }
  }

  @Operation(summary = "모든 메시지 조회", description = "모든 메시지 조회 API 입니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<PGetAllMessageResponseDto>>> getAllMessage(
      @RequestParam(required = false) String targetSlackId,
      @RequestParam(required = false) String content,
      @RequestParam(required = false) String messageType,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate sendAt,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size
  ) {
    PageResponse<PGetAllMessageResponseDto> responseDto = messageService.getAllMessage(
        targetSlackId,
        content,
        messageType,
        sendAt,
        page,
        size
    );

    return ResponseEntity.ok(new ApiResponse<>(
        MessageResponseCode.MESSAGE_FOUND.getMessage(),
        MessageResponseCode.MESSAGE_FOUND.getStatus().value(),
        responseDto
    ));
  }

  @Operation(summary = "openFeign: 주문 조회", description = "openFeign: 주문 조회 API 입니다.")
  @GetMapping("/order")
  public ResponseEntity<AGetOrderResponseDto> getOrder(
      @RequestParam UUID orderId
  ) {
    AGetOrderResponseDto orderByOrderId = orderClient.getOrderByOrderId(orderId);

    return ResponseEntity.ok(orderByOrderId);
  }

  @Operation(summary = "openFeign: 허브 조회", description = "openFeign: 허브 조회 API 입니다.")
  @GetMapping("/hub")
  public ResponseEntity<AGetHubResponseDto> getHub(
      @RequestParam List<UUID> hubId
  ) {
    AGetHubResponseDto orderByOrderId = hubClient.getOrderByOrderId(hubId);

    return ResponseEntity.ok(orderByOrderId);
  }

  @Operation(summary = "openFeign: 회원 조회", description = "openFeign: 회원 조회 API 입니다.")
  @GetMapping("/user")
  public ResponseEntity<AGetUserResponseDto> getUser(
      @RequestParam Long userId
  ) {
    AGetUserResponseDto userByUserId = userClient.getUserByUserId(userId);

    return ResponseEntity.ok(userByUserId);
  }

}
