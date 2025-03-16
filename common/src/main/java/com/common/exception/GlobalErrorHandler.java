package com.common.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.common.exception.type.ApiErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {

  private static void log(Throwable e, HttpServletRequest request, HttpStatus status) {
    log.error("{}:{}:{}:{}", request.getRequestURI(), status.value(), e.getClass().getSimpleName(),
        e.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException e, HttpServletRequest request) {
    List<ErrorResponse.ErrorField> errorFields = e.getConstraintViolations().stream()
        .map(violation -> new ErrorResponse.ErrorField(
            violation.getInvalidValue(),
            violation.getMessage()))
        .toList();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    log(e, request, status);
    return ResponseEntity
        .badRequest()
        .body(ErrorResponse.of(status, ApiErrorCode.INVALID_REQUEST.getMessage(), errorFields));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e,
      HttpServletRequest request) {
    List<ErrorResponse.ErrorField> errorFields = e.getBindingResult().getFieldErrors()
        .stream()
        .map(fieldError -> new ErrorResponse.ErrorField(
            fieldError.getRejectedValue(),
            fieldError.getDefaultMessage()))
        .toList();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    log(e, request, status);
    return ResponseEntity
        .badRequest()
        .body(ErrorResponse.of(status, ApiErrorCode.INVALID_REQUEST.getMessage(), errorFields));
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(
      HandlerMethodValidationException e,
      HttpServletRequest request) {
    List<ErrorResponse.ErrorField> errorFields = e.getValueResults()
        .stream()
        .map(result -> new ErrorResponse.ErrorField(
            result.getMethodParameter().getParameterName(),
            result.getResolvableErrors().get(0).getDefaultMessage()))
        .toList();

    HttpStatus status = HttpStatus.BAD_REQUEST;
    log(e, request, status);
    return ResponseEntity
        .badRequest()
        .body(ErrorResponse.of(status, ApiErrorCode.INVALID_REQUEST.getMessage(), errorFields));
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleException(CustomException e,
      HttpServletRequest request) {
    log(e, request, e.getErrorCode().getStatus());
    return ResponseEntity.status(e.getErrorCode().getStatus())
        .body(ErrorResponse.of(e.getErrorCode().getStatus(), e));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e,
      HttpServletRequest request) {
    HttpStatus status = INTERNAL_SERVER_ERROR;
    log(e, request, status);
    return ResponseEntity.status(status).body(ErrorResponse.of(status, e));
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<ErrorResponse> handleThrowable(Throwable e,
      HttpServletRequest request) {
    HttpStatus status = INTERNAL_SERVER_ERROR;
    log(e, request, status);
    return ResponseEntity.status(status).body(ErrorResponse.of(status, e));
  }
}
