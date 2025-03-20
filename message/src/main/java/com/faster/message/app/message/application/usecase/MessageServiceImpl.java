package com.faster.message.app.message.application.usecase;


import com.faster.message.app.message.application.client.DeliveryClient;
import com.faster.message.app.message.application.client.DeliveryManagerClient;
import com.faster.message.app.message.application.client.HubClient;
import com.faster.message.app.message.application.client.OrderClient;
import com.faster.message.app.message.application.client.UserClient;
import com.faster.message.app.message.application.dto.request.ASaveMessageRequestDto;
import com.faster.message.app.message.application.dto.response.AGetDeliveryManagerResponseDto;
import com.faster.message.app.message.application.dto.response.AGetDeliveryResponseDto;
import com.faster.message.app.message.application.dto.response.AGetHubResponseDto;
import com.faster.message.app.message.application.dto.response.AGetOrderResponseDto;
import com.faster.message.app.message.application.dto.response.AGetUserResponseDto;
import com.faster.message.app.message.application.dto.response.ASaveMessageResponseDto;
import com.faster.message.app.message.domain.entity.Message;
import com.faster.message.app.message.domain.repository.MessageRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;
  private final UserClient userClient;
  private final OrderClient orderClient;
  private final DeliveryClient deliveryClient;
  private final DeliveryManagerClient deliveryManagerClient;
  private final HubClient hubClient;

  private final ObjectMapper objectMapper;
  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${GEMINI_TOKEN}")
  private String geminiKey;

  private final static String GEMINI_API_URL =
      "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

  // TODO: 1. sendAt 시간에 맞춰서 어떻게 보내지?
  @Transactional
  @Override
  public ASaveMessageResponseDto saveAndSendMessageByHubManager(ASaveMessageRequestDto requestDto) {
    // 확보: 주문 고유 식별 번호, 주문자 이름, 주문자 Slack ID, 출발지, 경유지, 목적지, 배송 담당자
    // 없음: 메시지 내용V, 메시지 발송 일자V, 메시지 타입V, 요청사항V, 제품 이름V, 제품 수량V, 배송 담당자 슬랙 ID

    // 1. order-service: 제품 이름, 제품 수량, 요청사항
    AGetOrderResponseDto orderInfo = orderClient.getOrderByOrderId(requestDto.orderId());

    // 2. delivery-service: 배송 담당자 ID
    AGetDeliveryResponseDto deliveryResponseDto = deliveryClient.getDeliveryByOrderId(requestDto.deliveryId());

    // 3. delivery-manager-service: 배송 담당자 회원 ID
    AGetDeliveryManagerResponseDto deliveryManagerResponseDto = deliveryManagerClient.getOrderByOrderId(
        deliveryResponseDto.deliveryRouteList().get(0).deliveryManagerId());

    // 4. user-service: 배송 관리자 회원 이름, 슬랙 ID
    AGetUserResponseDto deliveryManagerInfo = userClient.getUserByUserId(deliveryManagerResponseDto.userId());

    // 5. hub-service: 발송 허브 담당자
    List<UUID> hubIds = deliveryResponseDto.deliveryRouteList().stream()
        .flatMap(route -> Stream.of(route.sourceHubId()))
        .toList();
    AGetHubResponseDto orderByOrderId = hubClient.getOrderByOrderId(hubIds);

    // 슬랙 보내야 한다.
    AGetUserResponseDto hubManagerInfo = userClient.getUserByUserId(orderByOrderId.hubInfos().get(0).managerId());

    // 메시지 생성 -> 상품명, 상품 수량, 요청 사항, 출발지, 경유지, 목적지
    String output = getString(requestDto, orderInfo, deliveryManagerInfo);

    // 메시지 저장
    Message message = Message.of(
        hubManagerInfo.slackId(),
        output,
        LocalDateTime.now());

    messageRepository.save(message);

    return ASaveMessageResponseDto.builder()
        .orderId(requestDto.orderId())
        .orderUserName(requestDto.orderUserName())
        .orderUserSlackId(requestDto.orderUserSlackId())

        .messageContent(output)
        .messageSendAt(LocalDateTime.now())
        .messageType(message.getMessageType().getType())
        .requestDetails(orderInfo.orderRequestMessage())

        .productName(orderInfo.orderItems().get(0).name())
        .productQuantity(orderInfo.orderItems().get(0).quantity())

        .hubWaypoint(requestDto.hubWaypoint())
        .hubDestination(requestDto.hubDestination())

        .deliveryManager(requestDto.deliveryManager())
        .deliveryManagerSlackId(deliveryManagerInfo.slackId())
        .build();
  }

  private String getString(ASaveMessageRequestDto requestDto,
                           AGetOrderResponseDto orderInfo,
                           AGetUserResponseDto deliveryManagerInfo) {
    String prompt = setupPrompt(requestDto, orderInfo);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-goog-api-key", geminiKey);

    Map<String, Object> body = getStringObjectMap(prompt);

    HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        GEMINI_API_URL,
        HttpMethod.POST,
        requestEntity,
        String.class);

    return extractTextFromResponse(response.getBody(), requestDto, orderInfo, deliveryManagerInfo);
  }

  private Map<String, Object> getStringObjectMap(String prompt) {
    return Map.of(
        "contents", new Object[]{
            Map.of("parts", new Object[]{
                Map.of("text", prompt)
            })
        }
    );
  }

  private String extractTextFromResponse(String responseBody,
                                         ASaveMessageRequestDto requestDto,
                                         AGetOrderResponseDto orderInfo,
                                         AGetUserResponseDto deliveryManagerInfo) {
    try {
      JsonNode root = objectMapper.readTree(responseBody);
      JsonNode textNode = root.path("candidates").get(0).path("content").path("parts").get(0).path("text");
      String extractedText = textNode.asText();

      return String.format(
          "주문 번호 : %s\n"
              + "주문자 정보 : %s / %s\n"
              + "상품 정보 : %s %d박스\n"
              + "요청 사항 : %s\n"
              + "발송지 : %s\n"
              + "경유지 : %s\n"
              + "도착지 : %s\n"
              + "배송담당자 : %s / %s\n\n"
              + "위 내용을 기반으로 도출된 최종 발송 시한은 %s 입니다.",
          requestDto.orderId().toString(),
          requestDto.orderUserName(), requestDto.orderUserSlackId(),
          orderInfo.orderItems().get(0).name(), orderInfo.orderItems().get(0).quantity(),
          orderInfo.orderRequestMessage(),
          requestDto.hubSource(),
          requestDto.hubWaypoint(),
          requestDto.hubDestination(),
          requestDto.deliveryManager(), deliveryManagerInfo,
          extractedText
      );
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse Gemini response", e);
    }
  }


  private String setupPrompt(ASaveMessageRequestDto requestDto, AGetOrderResponseDto orderInfo) {
    return String.format(
        "배송 마감 시간을 계산해야 합니다. 주어진 정보 안에서 답변 예제 형식을 반드시 지켜야 합니다. \n\n"
            + "- 상품명: %s\n"
            + "- 수량: %d\n"
            + "- 요청사항: %s\n"
            + "- 출발지: %s\n"
            + "- 경유지: %s\n"
            + "- 목적지: %s\n\n"
            + "배송 처리에 걸리는 예상 시간을 고려하여 출발 마감 시한을 정확히 계산하세요. \n"
            + "정확히 계산하지 못하는 경우에는 대략적인 계산을 수행합니다. \n\n"
            + "그외 다른 고려 사항은 제외입니다. \n\n"
            + "반드시 답변의 마지막 줄은 'MM월 dd일 HH시' 형식으로만 출력하세요.\n"
            + "예: 03월 15일 14시",
        orderInfo.orderItems().get(0).name(),
        orderInfo.orderItems().get(0).quantity(),
        orderInfo.orderRequestMessage(),
        requestDto.hubSource(),
        requestDto.hubWaypoint(),
        requestDto.hubDestination()
    );
  }
}