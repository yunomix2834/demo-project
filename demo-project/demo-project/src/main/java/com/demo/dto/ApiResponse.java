package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    // Mã phản hồi mặc định (200) cho các phản hồi thành công
    @Builder.Default
    int code = 200;

    String message;

    // Trạn thái phản hồi mặc định (mã 200) cho các phản hồi thành công
    @Builder.Default
    String status = "Thành công!";

    private T result;
}