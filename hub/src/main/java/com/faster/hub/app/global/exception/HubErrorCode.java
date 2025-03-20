package com.faster.hub.app.global.exception;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HubErrorCode implements ErrorCode {

  NOT_FOUND(HttpStatus.NOT_FOUND.value(), "존재하지 않는 허브입니다.", HttpStatus.NOT_FOUND),
  ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "갈 수 있는 경로가 없습니다.", HttpStatus.NOT_FOUND),
  INVALID_SORT_CONDITION(HttpStatus.UNPROCESSABLE_ENTITY.value(), "허브의 정렬 정보가 유효하지 않습니다.", HttpStatus.UNPROCESSABLE_ENTITY);

  private final int code;
  private final String message;
  private final HttpStatus status;
}
