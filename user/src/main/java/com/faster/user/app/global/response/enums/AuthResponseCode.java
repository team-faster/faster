package com.faster.user.app.global.response.enums;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthResponseCode implements ErrorCode {

  USER_SUCCESS(HttpStatus.OK.value(), "요청이 성공적으로 처리되었습니다.", HttpStatus.OK),
  USER_CREATED(HttpStatus.CREATED.value(),  "회원가입이 완료되었습니다.", HttpStatus.CREATED),
  USER_UPDATED(HttpStatus.OK.value(),  "회원 정보가 수정되었습니다.", HttpStatus.OK),
  USER_DELETED(HttpStatus.OK.value(),  "회원이 삭제되었습니다.", HttpStatus.OK),
  USER_FETCHED(HttpStatus.OK.value(), "회원 정보를 성공적으로 조회했습니다.", HttpStatus.OK),
  USERS_FETCHED(HttpStatus.OK.value(), "전체 회원 목록을 성공적으로 조회했습니다.", HttpStatus.OK),
  USER_SIGNED_IN(HttpStatus.OK.value(), "로그인에 성공했습니다.", HttpStatus.OK);

  private final int code;
  private final String message;
  private final HttpStatus status;
}
