package com.example.vkuserinfo.dto;

import lombok.Data;

@Data
public class ErrorResponse {

    private String message;

    private int status;

    public ErrorResponse(String message, int statusCode) {
        this.message = message;
        this.status = statusCode;
    }

}
