package com.example.vkuserinfo.service;

import com.example.vkuserinfo.dto.UserInfoRequest;
import com.example.vkuserinfo.dto.UserInfoResponse;
import com.example.vkuserinfo.exception.VkApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class VkApiService {

    private static final String VK_API_URL = "https://api.vk.com/method/";
    private static final String VK_API_VERSION = "5.199";

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable(value = "userInfo", key = "{#request.user_id, #request.group_id}")
    public UserInfoResponse getUserInfo(String serviceToken, UserInfoRequest userInfoRequest) throws VkApiException{

        Map<String, Object> userInfo = getUserData(serviceToken, userInfoRequest.getUser_id());

        // Проверяем членство в группе
        boolean isMember = checkGroupMembership(userInfoRequest.getUser_id(), userInfoRequest.getGroup_id(), serviceToken);

        // Формируем ответ
        UserInfoResponse response = new UserInfoResponse();
        response.setLast_name((String) userInfo.get("last_name"));
        response.setFirst_name((String) userInfo.get("first_name"));
        response.setNickname((String) userInfo.get("nickname"));
        response.setMember(isMember);

        return response;
    }

    public Map<String, Object> getUserData(String serviceToken, Long userId) throws VkApiException {

        String url = VK_API_URL + "users.get?user_ids=" + userId +
                "&fields=first_name,last_name,nickname" +
                "&access_token=" + serviceToken + "&v=" + VK_API_VERSION;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getBody().containsKey("error")) {
            throw new VkApiException((Map<String, Object>) response.getBody().get("error"));
        }

        return (Map<String, Object>) ((java.util.List) response.getBody().get("response")).get(0);

    }

    private boolean checkGroupMembership(Long userId, Long groupId, String token) throws VkApiException {
        String url = VK_API_URL + "groups.isMember?user_id=" + userId +
                "&group_id=" + groupId +
                "&access_token=" + token + "&v=" + VK_API_VERSION;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getBody().containsKey("error")) {
            throw new VkApiException((Map<String, Object>) response.getBody().get("error"));
        }

        return (Integer) response.getBody().get("response") == 1;
    }

}
