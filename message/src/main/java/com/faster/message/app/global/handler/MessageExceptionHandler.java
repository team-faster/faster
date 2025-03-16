package com.faster.message.app.global.handler;

import com.common.exception.ErrorResponse;
import com.faster.message.app.global.MessageInvalidBySendAtException;
import com.faster.message.app.global.MessageInvalidBySlackIdException;
import com.faster.message.app.global.enums.MessageErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MessageExceptionHandler {

  @ExceptionHandler(MessageInvalidBySendAtException.class)
  public ResponseEntity<ErrorResponse> MessageInvalidBySendAtException(MessageInvalidBySendAtException e) {
    MessageErrorCode errorCode = MessageErrorCode.MESSAGE_INVALID_SEND_AT;

    ErrorResponse errorResponse = new ErrorResponse(
        errorCode.getCode(),
        errorCode.getStatus(),
        errorCode.getMessage(),
        null
    );
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }


  @ExceptionHandler(MessageInvalidBySlackIdException.class)
  public ResponseEntity<ErrorResponse> MessageInvalidBySlackIdException(MessageInvalidBySlackIdException e) {
    MessageErrorCode errorCode = MessageErrorCode.MESSAGE_INVALID_SLACK_ID;

    ErrorResponse errorResponse = new ErrorResponse(
        errorCode.getCode(),
        errorCode.getStatus(),
        errorCode.getMessage(),
        null
    );
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }
}
