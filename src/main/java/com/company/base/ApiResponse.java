package com.company.base;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        String message,
        Integer code,
        Boolean status,
        T data
) {
    public ApiResponse(String message, Integer code, Boolean status) {
        this(message, code, status, null);
    }

    public ApiResponse(Boolean status, String message) {
        this(message, null, status, null);
    }

    public ApiResponse(Boolean status, T data) {
        this(null, null, status, data);
    }

    public ApiResponse(Boolean status, String message, T data) {
        this(message, null, status, data);
    }
}
