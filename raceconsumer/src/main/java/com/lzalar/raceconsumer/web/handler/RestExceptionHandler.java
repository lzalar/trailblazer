package com.lzalar.raceconsumer.web.handler;


import com.lzalar.raceconsumer.domain.exception.ApiError;
import com.lzalar.raceconsumer.domain.exception.ErrorCode;
import com.lzalar.raceconsumer.domain.exception.TrailblazerException;
import com.lzalar.raceconsumer.domain.exception.ViolationError;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.lzalar.raceconsumer.domain.exception.ErrorCode.*;
import static java.util.Collections.singletonList;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(TrailblazerException.class)
  protected ResponseEntity<Object> handleTrailblazerException(TrailblazerException ex) {
    log.debug("TrailblazerException application exception");
    return createResponseEntity(ex, ex.getErrorCode());
  }

  @ExceptionHandler(AuthenticationException.class)
  protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
    log.debug("Authentication exception", ex);
    return createResponseEntity(ex, UNAUTHORIZED);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
    log.debug("Constraint violation exception", ex);
    return createResponseEntity(ex, CONSTRAINT_VIOLATION);
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
    log.debug("Authentication exception", ex);
    return createResponseEntity(ex, ACCESS_DENIED);
  }

  @ExceptionHandler({Exception.class, RuntimeException.class})
  public ResponseEntity<Object> handleAll(Exception ex) {
    log.error("Unexpected exception", ex);
    return createResponseEntity(ex, INTERNAL_ERROR);
  }

  private ResponseEntity<Object> createResponseEntity(Exception ex, ErrorCode errorCode) {
    return new ResponseEntity<>(
      new ApiError(errorCode.getCode(), errorCode.getMessage(), singletonList((new ViolationError(errorCode.getCode(), ex.getMessage())))),
      new HttpHeaders(),
      errorCode.getStatus());
  }
}
