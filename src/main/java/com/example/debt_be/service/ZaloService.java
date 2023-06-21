package com.example.debt_be.service;


import com.example.debt_be.entity.dto.*;
import com.example.debt_be.entity.model.ZaloMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZaloService {
    @Value("${zalo.api.url}")
    private String zaloApiUrl;

    @Value("${zalo.appId}")
    private String zaloAppId;

    @Value("${zalo.appSecret}")
    private String zaloAppSecret;



    @Autowired
    private RestTemplate restTemplate;


    public String buildAuthorizationUrl(String redirectUrl) {
        String authUrl = "https://oauth.zaloapp.com/v3/auth?app_id=" + zaloAppId + "&redirect_uri=" + redirectUrl;
        return authUrl;
    }

    public ZaloAccessTokenResponse getAccessToken(String code) {
        String accessTokenUrl = "https://oauth.zaloapp.com/v3/access_token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("app_id", zaloAppId);
        body.add("app_secret", zaloAppSecret);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<ZaloAccessTokenResponse> responseEntity = restTemplate.postForEntity(
                accessTokenUrl,
                requestEntity,
                ZaloAccessTokenResponse.class);

        return responseEntity.getBody();
    }

    public ZaloUserProfileResponse getUserProfile(String accessToken) {
        String userProfileUrl = zaloApiUrl + "/me?access_token=" + accessToken;

        ResponseEntity<ZaloUserProfileResponse> responseEntity = restTemplate.getForEntity(
                userProfileUrl,
                ZaloUserProfileResponse.class);

        return responseEntity.getBody();
    }

    public void sendZaloMessage(String accessToken, ZaloMessageRequestDTO messageRequest) {
        String sendMessageUrl = zaloApiUrl + "/message?access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ZaloMessageRequestDTO> requestEntity = new HttpEntity<>(messageRequest, headers);

        restTemplate.postForObject(sendMessageUrl, requestEntity, Void.class);
    }

    public String getAccessTokenn() {
        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set request parameters
        String url = "https://oauth.zaloapp.com/v3/access_token";
        String body = String.format("{\"app_id\": \"%s\", \"app_secret\": \"%s\"}", zaloAppId, zaloAppSecret);

        // Send POST request to obtain the access token
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), String.class);

        // Parse the access token from the response
        String accessToken = response.getBody();

        return accessToken;
    }

}
