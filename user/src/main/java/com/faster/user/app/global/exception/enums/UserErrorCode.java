package com.faster.user.app.global.exception.enums;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

  // 회원 관련 오류
  USER_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND.value(), "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  DUPLICATE_USER_ID(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST),
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 입력 값입니다.", HttpStatus.BAD_REQUEST),
  UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED.value(), "인증되지 않은 접근입니다.", HttpStatus.UNAUTHORIZED);

  private final int code;
  private final String message;
  private final HttpStatus status;
}