package com.faster.user.app.global.exception.handler;

import com.common.exception.ErrorResponse;
import com.faster.user.app.global.exception.UserAlreadyExistsBySlackIdException;
import com.faster.user.app.global.exception.UserAlreadyExistsByUsernameException;
import com.faster.user.app.global.exception.enums.AuthErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

  @ExceptionHandler(UserAlreadyExistsByUsernameException.class)
  public ResponseEntity<ErrorResponse> UserAlreadyExistsByUsernameException(UserAlreadyExistsByUsernameException e) {
    AuthErrorCode errorCode = AuthErrorCode.SIGNUP_DUPLICATE_USERNAME;

    ErrorResponse errorResponse = new ErrorResponse(
        errorCode.getCode(),
        errorCode.getStatus(),
        errorCode.getMessage(),
        null
    );
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }

  @ExceptionHandler(UserAlreadyExistsBySlackIdException.class)
  public ResponseEntity<ErrorResponse> UserAlreadyExistsBySlackIdException(UserAlreadyExistsBySlackIdException e) {
    AuthErrorCode errorCode = AuthErrorCode.SIGNUP_DUPLICATE_USERNAME;

    ErrorResponse errorResponse = new ErrorResponse(
        errorCode.getCode(),
        errorCode.getStatus(),
        errorCode.getMessage(),
        null
    );
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }
}