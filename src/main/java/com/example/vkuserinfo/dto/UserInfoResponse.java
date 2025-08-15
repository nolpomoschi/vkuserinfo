package com.example.vkuserinfo.dto;

import lombok.Data;

@Data
public class UserInfoResponse {

    private String first_name;

    private String last_name;

    private String nickname;

    private boolean member;

}
