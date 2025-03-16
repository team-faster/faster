package com.faster.message.app.global;

public class MessageInvalidBySendAtException extends RuntimeException {
  public MessageInvalidBySendAtException(String message) {
    super(message);
  }
}
