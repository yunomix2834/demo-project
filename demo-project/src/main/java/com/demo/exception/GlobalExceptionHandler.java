package com.demo.exception;

import com.demo.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  /**
   * Xử lý các ngoại lệ RuntimeException không được bắt riêng.
   *
   * @param exception ngoại lệ RuntimeException được ném ra
   * @return ResponseEntity chứa ApiResponse với mã lỗi, thông điệp và trạng thái lỗi tương ứng
   */
  @ExceptionHandler(value = Exception.class)
  ResponseEntity<ApiResponse<?>> handlingRuntimeException(
      RuntimeException exception) {
    log.error("Exception: " + exception.getMessage(), exception);

    ApiResponse<?> apiResponse = new ApiResponse<>();
    apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
    apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
    apiResponse.setStatus(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatus());

    return ResponseEntity.badRequest().body(apiResponse);
  }

  /**
   * Xử lý ngoại lệ ứng dụng (AppException) và chuyển thành ApiResponse.
   *
   * @param exception ngoại lệ AppException được ném ra
   * @return ResponseEntity chứa ApiResponse với thông tin lỗi tương ứng
   */
  @ExceptionHandler(value = AppException.class)
  ResponseEntity<ApiResponse<?>> handlingAppException(
      AppException exception) {
    log.error("Exception: " + exception.getMessage(), exception);

    ErrorCode errorCode = exception.getErrorCode();
    ApiResponse<?> apiResponse = new ApiResponse<>();
    apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessage(errorCode.getMessage());
    apiResponse.setStatus(errorCode.getStatus());

    return ResponseEntity.status(errorCode.getStatusCode())
        .body(apiResponse);
  }

  /**
   * Xử lý ngoại lệ AccessDeniedException khi người dùng không có quyền truy cập.
   *
   * @param exception ngoại lệ AccessDeniedException
   * @return ResponseEntity chứa ApiResponse với thông tin lỗi truy cập
   */
  @ExceptionHandler(value = AccessDeniedException.class)
  ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(
      AccessDeniedException exception) {
    ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    ApiResponse<?> apiResponse = ApiResponse.builder()
        .code(errorCode.getCode())
        .message(errorCode.getMessage())
        .status(errorCode.getStatus())
        .build();

    return ResponseEntity.status(errorCode.getStatusCode())
        .body(apiResponse);
  }
}