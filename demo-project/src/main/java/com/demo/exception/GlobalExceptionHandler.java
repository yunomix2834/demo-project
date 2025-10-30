package com.demo.exception;

import com.demo.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  ResponseEntity<ErrorResponse> handlingRuntimeException(
          RuntimeException exception,
          WebRequest webRequest) {
    ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(errorCode.getCode());
    errorResponse.setMessage(errorCode.getMessage());
    errorResponse.setStatus(errorCode.getStatus());
    errorResponse.setApiPath(webRequest.getDescription(false));
    errorResponse.setErrorTime(LocalDateTime.now());

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Xử lý ngoại lệ AccessDeniedException khi người dùng không có quyền truy cập.
   *
   * @param exception ngoại lệ AccessDeniedException
   * @return ResponseEntity chứa ApiResponse với thông tin lỗi truy cập
   */
  @ExceptionHandler(value = AccessDeniedException.class)
  ResponseEntity<ErrorResponse> handlingAccessDeniedException(
      AccessDeniedException exception,
      WebRequest webRequest) {

    ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(errorCode.getCode());
    errorResponse.setMessage(errorCode.getMessage());
    errorResponse.setStatus(errorCode.getStatus());
    errorResponse.setApiPath(webRequest.getDescription(false));
    errorResponse.setErrorTime(LocalDateTime.now());

    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }


    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ErrorResponse> handlingAppException(
            AppException exception,
            WebRequest webRequest) {

        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(errorCode.getCode());
        errorResponse.setMessage(errorCode.getMessage());
        errorResponse.setStatus(errorCode.getStatus());
        errorResponse.setApiPath(webRequest.getDescription(false));
        errorResponse.setErrorTime(LocalDateTime.now());

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(errorResponse);
    }
}