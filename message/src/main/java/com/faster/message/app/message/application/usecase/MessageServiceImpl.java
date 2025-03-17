package com.faster.message.app.message.application.usecase;


import static com.faster.message.app.global.enums.MessageErrorCode.*;
import static com.faster.message.app.global.enums.MessageErrorCode.MESSAGE_INVALID_SEND_AT;

import com.common.exception.CustomException;
import com.faster.message.app.global.enums.MessageErrorCode;
import com.faster.message.app.message.application.dto.SaveGeminiMessageRequestDto;
import com.faster.message.app.message.application.dto.SaveMessageRequestDto;
import com.faster.message.app.message.domain.entity.Message;
import com.faster.message.app.message.domain.enums.MessageType;
import com.faster.message.app.message.domain.repository.MessageRepository;
import com.faster.message.app.message.presentation.dto.response.SaveMessageResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
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
  private final ObjectMapper objectMapper;
  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${GEMINI_TOKEN}")
  private String geminiKey;

  private final static String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

  private void checkDateTimeBySendAt(LocalDateTime sendAt) {
    if (sendAt.isBefore(LocalDateTime.now())) {
      throw new CustomException(MESSAGE_INVALID_SEND_AT);
    }
  }

  private void checkSlackId(String targetSlackId) {
    // TODO: 슬랙 아이디 검증 로직 추가
  }

  private void checkMessageType(MessageType messageType) {
    if (messageType == null) {
      throw new CustomException(MESSAGE_TYPE_NULL);
    }

    boolean isValid = Arrays.stream(MessageType.values())
        .anyMatch(validType -> validType == messageType);

    if (!isValid) {
      throw new CustomException(MESSAGE_TYPE_INVALID);
    }
  }


  @Transactional
  @Override
  public SaveMessageResponseDto saveMessage(SaveMessageRequestDto requestDto) {
    checkDateTimeBySendAt(requestDto.sendAt());

    checkSlackId(requestDto.targetSlackId());
    // 3. TODO: 올바른 타입인지 확인하기

    Message message = Message.of(
        requestDto.targetSlackId(),
        requestDto.contents(),
        requestDto.messageType(),
        requestDto.sendAt());

    Message savedMessage = messageRepository.save(message);

    return SaveMessageResponseDto.from(savedMessage);
  }

  // TODO: sendAt 시간에 맞춰서 어떻게 보내지?
  @Override
  public String saveGeminiMessage(SaveGeminiMessageRequestDto requestDto) {
    String prompt = setupPrompt(requestDto);
    checkDateTimeBySendAt(requestDto.sendAt());

    // 2. TODO: 슬랙 아이디가 존재하는지 확인하기
    checkSlackId(requestDto.targetSlackId());

    checkMessageType(requestDto.messageType());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-goog-api-key", geminiKey);

    Map<String, Object> body = getStringObjectMap(prompt);

    HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    ResponseEntity<String> response = restTemplate.exchange(GEMINI_API_URL, HttpMethod.POST, requestEntity, String.class);

    String output = extractTextFromResponse(response.getBody(), requestDto);

    Message message = Message.of(
        requestDto.targetSlackId(),
        output,
        requestDto.messageType(),
        requestDto.sendAt());

    messageRepository.save(message);

    return output;
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

  private String extractTextFromResponse(String responseBody, SaveGeminiMessageRequestDto requestDto) {
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
          requestDto.name(),
          requestDto.targetSlackId(),
          requestDto.productName(),
          requestDto.quantity(),
          requestDto.requestDetails(),
          requestDto.source(),
          requestDto.waypoint(),
          requestDto.destination(),
          requestDto.deliveryManager(),
          requestDto.deliveryManagerSlackId(),
          extractedText
      );
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse Gemini response", e);
    }
  }


  private String setupPrompt(SaveGeminiMessageRequestDto requestDto) {
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
        requestDto.productName(),
        requestDto.quantity(),
        requestDto.requestDetails(),
        requestDto.source(),
        requestDto.waypoint(),
        requestDto.destination()
    );
  }
}