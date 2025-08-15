package com.example.vkuserinfo.controller;

import com.example.vkuserinfo.dto.ErrorResponse;
import com.example.vkuserinfo.dto.UserInfoRequest;
import com.example.vkuserinfo.dto.UserInfoResponse;
import com.example.vkuserinfo.exception.VkApiException;
import com.example.vkuserinfo.service.VkApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class VkApiController {

    @Autowired
    private VkApiService vkApiService;

    @PostMapping("/user-info")
    public ResponseEntity<?> getUserInfo(@Header("vk_service_token") String serviceToken, @Valid @RequestBody UserInfoRequest userInfoRequest) {

        try {

            UserInfoResponse response = vkApiService.getUserInfo(serviceToken, userInfoRequest);
            return ResponseEntity.ok(response);

        } catch (VkApiException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), e.getStatusCode()));

        }

    }

}
