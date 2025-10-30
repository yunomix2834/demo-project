package com.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import static com.demo.constant.exception.ErrorCodeConstant.CONFLICT_STATUS;
import static com.demo.constant.exception.ErrorCodeConstant.FORBIDDEN_STATUS;
import static com.demo.constant.exception.ErrorCodeConstant.INTERNAL_SERVER_STATUS;
import static com.demo.constant.exception.ErrorCodeConstant.NOT_FOUND_STATUS;
import static com.demo.constant.exception.ErrorCodeConstant.UNAUTHORIZED_STATUS;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ErrorCode {
  // Các lỗi không phân loại và lỗi chung
  UNCATEGORIZED_EXCEPTION(999, INTERNAL_SERVER_STATUS,
      "Lỗi chưa phân loại", INTERNAL_SERVER_ERROR),

  // 400 - Bad Request

  // 401 - Unauthorized
  UNAUTHENTICATED(401, UNAUTHORIZED_STATUS, "Chưa xác thực!",
      HttpStatus.UNAUTHORIZED),
  INVALID_CREDENTIALS(401, UNAUTHORIZED_STATUS,
      "Thông tin đăng nhập không hợp lệ!",
      HttpStatus.UNAUTHORIZED),
  INVALID_TOKEN(401, UNAUTHORIZED_STATUS, "Token không hợp lệ!",
      HttpStatus.UNAUTHORIZED),
  TOKEN_REVOKED(401, UNAUTHORIZED_STATUS, "Token đã bị thu hồi!",
      HttpStatus.UNAUTHORIZED),

  // 403 - Forbidden
  UNAUTHORIZED(403, FORBIDDEN_STATUS, "Bạn không có quyền truy cập!",
      FORBIDDEN),

  // 404 - Not Found
  HELLO_NOT_FOUND(404, NOT_FOUND_STATUS, "Không tìm thấy hello!",
      NOT_FOUND),


  // 409 - Conflict
  USER_ALREADY_EXISTS(4098301, CONFLICT_STATUS, "Người dùng đã tồn tại!",
      CONFLICT),
  EMAIL_ALREADY_EXISTS(4098302, CONFLICT_STATUS, "Email đã tồn tại!",
      CONFLICT),
  PASSWORD_ALREADY_EXISTS(4098303, CONFLICT_STATUS, "Mật khẩu đã tồn tại!",
      CONFLICT),

  // 500 - Internal Server Error
  FAILED_GENERATE_TOKEN(5008301, INTERNAL_SERVER_STATUS,
      "Lỗi tạo JWT token!", INTERNAL_SERVER_ERROR),
  FAILED_VALIDATE_TOKEN(5008302, INTERNAL_SERVER_STATUS,
      "Lỗi xác thực token!", INTERNAL_SERVER_ERROR),

  ;

  private final int code;
  private final String status;
  private final String message;
  private final HttpStatusCode statusCode;

  ErrorCode(
      int code,
      String status,
      String message,
      HttpStatusCode statusCode) {
    this.code = code;
    this.status = status;
    this.message = message;
    this.statusCode = statusCode;
  }
}