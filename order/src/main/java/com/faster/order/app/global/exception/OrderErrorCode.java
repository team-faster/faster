package com.faster.order.app.global.exception;

import com.common.exception.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

  NOT_ENOUGH_TOTAL_PRICE(HttpStatus.UNPROCESSABLE_ENTITY.value(),
      "결제 금액이 최소 결제 금액 3000원보다 적습니다.",
      HttpStatus.UNPROCESSABLE_ENTITY),
  INVALID_ORDER_ID(HttpStatus.NOT_FOUND.value(),
      "존재하지 않는 주문 아이디입니다.",
      HttpStatus.NOT_FOUND),;

  private final int code;
  private final String message;
  private final HttpStatus httpStatus;

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public HttpStatus getStatus() {
    return httpStatus;
  }

  @Override
  public int getHttpStatus() {
    return ErrorCode.super.getHttpStatus();
  }
}
