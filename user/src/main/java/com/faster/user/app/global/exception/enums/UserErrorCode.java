package com.faster.user.app.global.exception.enums;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

  // 회원 관련 오류
  USER_NOT_FOUND_BY_ID(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST);


  private final int code;
  private final String message;
  private final HttpStatus status;
}
