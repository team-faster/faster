package com.faster.product.app.global.exception;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {
  INVALID_ID(
      HttpStatus.NOT_FOUND.value(),
      "존재하지 않는 상품 아이디입니다.",
      HttpStatus.NOT_FOUND),
  FORBIDDEN_ACCESS(
      HttpStatus.FORBIDDEN.value(),
      "권한이 없는 접근입니다.",
      HttpStatus.FORBIDDEN),
  NOT_ENOUGH_STOCK(
      HttpStatus.UNPROCESSABLE_ENTITY.value(),
      "재고 수량이 충분하지 않습니다.",
      HttpStatus.UNPROCESSABLE_ENTITY),
  NOT_SUPPLIER(
      HttpStatus.UNPROCESSABLE_ENTITY.value(),
      "공급 업체가 아니므로 상품을 등록할 수 없습니다.",
      HttpStatus.UNPROCESSABLE_ENTITY),
  INVALID_SORT_CONDITION(
      HttpStatus.UNPROCESSABLE_ENTITY.value(),
      "상품 검색의 정렬 조건이 부적합합니다.",
      HttpStatus.UNPROCESSABLE_ENTITY),
  INVALID_PERIOD(
      HttpStatus.UNPROCESSABLE_ENTITY.value(),
      "상품 검색의 기간 조건이 부적합합니다.",
      HttpStatus.UNPROCESSABLE_ENTITY);

  private final int code;
  private final String message;
  private final HttpStatus status;
}
