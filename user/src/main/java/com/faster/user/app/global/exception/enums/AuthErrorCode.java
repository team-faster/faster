package com.faster.user.app.global.exception.enums;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

  // Signup (회원가입 관련 오류)
  SIGNUP_DUPLICATE_USERNAME(HttpStatus.CONFLICT.value(), "이미 존재하는 아이디입니다.", HttpStatus.CONFLICT),
  SIGNUP_DUPLICATE_SLACK_ID(HttpStatus.CONFLICT.value(), "이미 존재하는 슬랙 ID 입니다.", HttpStatus.CONFLICT),
  SIGNUP_INVALID_USERNAME_FORMAT(HttpStatus.BAD_REQUEST.value(), "아이디 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
  SIGNUP_INVALID_SLACK_ID_FORMAT(HttpStatus.BAD_REQUEST.value(), "슬랙 ID 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
  SIGNUP_PASSWORD_TOO_WEAK(HttpStatus.BAD_REQUEST.value(), "비밀번호가 보안 기준을 충족하지 않습니다.", HttpStatus.BAD_REQUEST),
  SIGNUP_MISSING_REQUIRED_FIELDS(HttpStatus.BAD_REQUEST.value(), "필수 입력 항목이 누락되었습니다.", HttpStatus.BAD_REQUEST),

  // SignIn (로그인 관련 오류)
  SIGN_IN_INVALID_USERNAME(HttpStatus.BAD_REQUEST.value(), "아이디가 틀렸습니다.", HttpStatus.BAD_REQUEST),
  SIGN_IN_INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(), "비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);

  private final int code;
  private final String message;
  private final HttpStatus status;
}
