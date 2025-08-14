package com.example.vkuserinfo.exception;

import lombok.Getter;
import java.util.Map;

@Getter
public class VkApiException extends Exception {

    private final int statusCode;

    public VkApiException(Map<String, Object> error) {

        super((String) error.get("error_msg"));
        this.statusCode = (int) error.get("error_code");

    }

    public int getStatusCode() {

        return statusCode;

    }

}
