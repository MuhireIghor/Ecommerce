package com.ne.template.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponse {
    private String message;
    private Boolean success;
    private Object data;

    public ApiResponse(Boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public ApiResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(Boolean success,String message, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

}
