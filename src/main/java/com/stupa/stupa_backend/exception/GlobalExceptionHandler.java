package com.stupa.stupa_backend.exception;

import com.stupa.stupa_backend.dto.response.BaseResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final String VALIDATION_ERROR = "VALIDATION_ERROR";

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<BaseResponse<String>> handleBusinessException(BusinessException e) {
    return ResponseEntity.status(e.getStatus()).body(BaseResponse.<String>builder()
        .error(e.getErrorCode())
        .message(e.getMessage())
        .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<BaseResponse<Map<String, String>>> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      WebRequest request) {

    log.error(
        "#handleMethodArgumentNotValid - error occurred with request: {}",
        request.toString());

    Map<String, String> errors = mapMethodArgumentNotValidException(
        exception);

    BaseResponse<Map<String, String>> response = BaseResponse.<Map<String, String>>builder()
        .error(VALIDATION_ERROR)
        .data(errors)
        .build();

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<BaseResponse> handleIllegalArgumentException(
      IllegalArgumentException e) {
    return ResponseEntity.badRequest().body(BaseResponse.<String>builder()
        .error("ILLEGAL_ARGUMENT")
        .message("Data integrity violation")
        .build());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<BaseResponse> handleDataIntegrityViolationException(
      DataIntegrityViolationException e) {
    return ResponseEntity.badRequest().body(BaseResponse.<String>builder()
        .message("Data integrity violation")
        .build());
  }

  @ExceptionHandler(HttpMediaTypeException.class)
  public ResponseEntity<BaseResponse> handleBusinessException(HttpMediaTypeException e) {
    return ResponseEntity.badRequest().body(BaseResponse.<String>builder()
        .error("INVALID_MEDIA_TYPE")
        .message(e.getMessage())
        .build());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<BaseResponse> handleBusinessException(
      HttpRequestMethodNotSupportedException e) {
    return ResponseEntity.badRequest().body(BaseResponse.<String>builder()
        .error("METHOD_NOT_SUPPORTED")
        .message(e.getMessage())
        .build());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<BaseResponse> handleBusinessException(HttpMessageNotReadableException e) {
    return ResponseEntity.badRequest().body(BaseResponse.<String>builder()
        .error("CAN_NOT_READ_MESSAGE")
        .message(e.getMessage())
        .build());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<BaseResponse> handleAllExceptions(Exception ex, WebRequest request) {
    log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
    return ResponseEntity.internalServerError().body(BaseResponse.<String>builder()
        .error("INTERNAL_SERVER_ERROR")
        .message("An unexpected error occurred")
        .build());
  }

  private Map<String, String> mapMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {

    HashMap<String, String> mapResult = new HashMap<>();
    exception.getBindingResult().getFieldErrors()
        .forEach(error -> {
          mapResult.put(error.getField(), error.getDefaultMessage());
        });

    return mapResult;
  }
}
