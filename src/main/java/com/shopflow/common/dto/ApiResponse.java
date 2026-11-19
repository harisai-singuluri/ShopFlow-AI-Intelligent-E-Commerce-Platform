package com.shopflow.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API response wrapper used across the application.
 *
 * <p>Professional teams often wrap responses to provide a stable contract
 * and to include meta‑information (e.g., pagination, timestamps) later.
 * For now we keep it simple: a data field and an optional error field.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private ErrorInfo error;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, null);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(null, new ErrorInfo(code, message));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorInfo {
        private String code;
        private String message;
    }
}
