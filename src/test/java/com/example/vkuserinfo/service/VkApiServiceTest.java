package com.example.vkuserinfo.service;

import com.example.vkuserinfo.dto.UserInfoRequest;
import com.example.vkuserinfo.dto.UserInfoResponse;
import com.example.vkuserinfo.exception.VkApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VkApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VkApiService vkApiService;

    @Test
    public void testGetUserInfo() throws VkApiException {

        UserInfoRequest request = new UserInfoRequest();
        request.setUser_id(123L);
        request.setGroup_id(456L);

        Map<String, Object> mockUser = Map.of(
                "last_name", "Иванов",
                "first_name", "Иван",
                "nickname", "Иванович"
        );
        Map<String, Object> mockUserResponse = Map.of(
                "response", List.of(mockUser)
        );

        Map<String, Object> mockGroupResponse = Map.of(
                "response", 1
        );

        when(restTemplate.getForEntity(
                contains("users.get"),
                eq(Map.class)
        )).thenReturn(ResponseEntity.ok(mockUserResponse));

        when(restTemplate.getForEntity(
                contains("groups.isMember"),
                eq(Map.class)
        )).thenReturn(ResponseEntity.ok(mockGroupResponse));

        UserInfoResponse response = vkApiService.getUserInfo("token", request);

        assertEquals("Иванов", response.getLast_name());
        assertEquals("Иван", response.getFirst_name());
        assertEquals("Иванович", response.getNickname());
        assertTrue(response.isMember());

    }

}
