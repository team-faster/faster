package com.faster.message.app.global.response;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MessageResponseCode {

  // 메시지 관련 성공 응답 코드
  MESSAGE_CREATED("메시지가 성공적으로 생성되었습니다.", HttpStatus.CREATED),
  MESSAGE_UPDATED("메시지가 성공적으로 수정되었습니다.", HttpStatus.OK),
  MESSAGE_DELETED("메시지가 성공적으로 삭제되었습니다.", HttpStatus.OK),
  MESSAGE_RETRIEVED("메시지가 성공적으로 조회되었습니다.", HttpStatus.OK);

  private final String message;
  private final HttpStatus status;
}