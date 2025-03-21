package com.faster.message.app.global.enums;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MessageErrorCode implements ErrorCode {

  // Message (메시지 관련 오류)
  MESSAGE_INVALID_SEND_AT(HttpStatus.BAD_REQUEST.value(), "현재 시간보다 이전으로 메시지를 보낼 수 없습니다.", HttpStatus.BAD_REQUEST),
  MESSAGE_INVALID_SLACK_ID(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 Slack ID 입니다.", HttpStatus.BAD_REQUEST),
  MESSAGE_TYPE_NULL(HttpStatus.BAD_REQUEST.value(), "메시지 타입이 비어 있습니다.", HttpStatus.BAD_REQUEST),
  MESSAGE_TYPE_INVALID(HttpStatus.BAD_REQUEST.value(), "메시지 타입이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),

  // Slack
  SLACK_OPEN_DM_FAILED(HttpStatus.BAD_REQUEST.value(), "DM 채널을 열 수 없습니다.", HttpStatus.BAD_REQUEST),
  SLACK_CREATE_DM_FAILED(HttpStatus.BAD_REQUEST.value(), "DM 채널을 생성할 수 없습니다.", HttpStatus.BAD_REQUEST);


  private final int code;
  private final String message;
  private final HttpStatus status;
}
