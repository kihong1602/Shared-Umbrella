package oo.kr.shared.global.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
    String code,
    String message
) {

  public static ErrorResponse create(HttpStatus httpStatus, String message) {
    return new ErrorResponse(httpStatus.name(), message);
  }
}