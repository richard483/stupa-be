package com.stupa.stupa_backend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

  private String errorCode;
  private Object data;
  private HttpStatus status = HttpStatus.BAD_REQUEST;

  public BusinessException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
    this.data = null;
  }

  public BusinessException(String errorCode, String message, HttpStatus httpStatus) {
    super(message);
    this.errorCode = errorCode;
    this.data = null;
    this.status = httpStatus;

  }

}
