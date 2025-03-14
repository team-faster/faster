package com.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApiErrorCode implements ErrorCode {
  INVALID_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "인증되지 않은 요청입니다.", HttpStatus.UNAUTHORIZED),
  FORBIDDEN(HttpStatus.FORBIDDEN.value(), "접근이 금지 되었습니다.", HttpStatus.FORBIDDEN),
  NOT_FOUND(HttpStatus.NOT_FOUND.value(), "요청한 데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 에러 입니다.",
      HttpStatus.INTERNAL_SERVER_ERROR),
  NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED.value(), "요청한 URI 페이지는 없습니다.",
      HttpStatus.NOT_IMPLEMENTED),
  BAD_GATEWAY(HttpStatus.BAD_GATEWAY.value(), "서버 간 통신이 올바르지 않습니다.", HttpStatus.BAD_GATEWAY);

  private final int code;
  private final String message;
  private final HttpStatus status;
}
