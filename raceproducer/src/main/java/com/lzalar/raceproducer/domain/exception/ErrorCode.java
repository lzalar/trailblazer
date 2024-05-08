package com.lzalar.raceproducer.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

  EXCEPTION(40001, "Exception occurred", BAD_REQUEST),
  UNAUTHORIZED(40100, "Unauthorized", HttpStatus.UNAUTHORIZED),
  ACCESS_DENIED(40301, "Forbidden", FORBIDDEN),
  USER_NOT_FOUND(40001, "User not found", BAD_REQUEST),
  RACE_NOT_FOUND(40002, "User not found", BAD_REQUEST),
  CONSTRAINT_VIOLATION(40003, "Constraint violation", BAD_REQUEST),
  INTERNAL_ERROR(50001, "Internal Server Error", INTERNAL_SERVER_ERROR);

  private final int code;
  private final String message;
  private final HttpStatus status;

  ErrorCode(int code, String message, HttpStatus status) {
    this.code = code;
    this.message = message;
    this.status = status;
  }

}
