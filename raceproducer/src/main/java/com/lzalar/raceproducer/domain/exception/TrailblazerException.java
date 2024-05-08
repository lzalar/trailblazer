package com.lzalar.raceproducer.domain.exception;

import lombok.Getter;

@Getter
public class TrailblazerException extends RuntimeException {

  private final ErrorCode errorCode;

  public TrailblazerException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public TrailblazerException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
  }

  @Override
  public String toString() {
    return "TrailblazerException { " +
      "errorCode=" + errorCode.getCode() + " , description=" + errorCode.getMessage() +
      '}';
  }
}
