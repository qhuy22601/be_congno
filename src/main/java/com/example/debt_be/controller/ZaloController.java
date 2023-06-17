package com.example.debt_be.controller;

import com.example.debt_be.entity.dto.ZaloAccessTokenResponse;
import com.example.debt_be.entity.dto.ZaloUserProfileResponse;
import com.example.debt_be.entity.dto.ZaloMessageRequestDTO;
import com.example.debt_be.entity.dto.ZaloTemplateMessageRequest;
import com.example.debt_be.entity.model.ZaloMessageRequest;
import com.example.debt_be.service.ZaloService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/zalo")
public class ZaloController {
    @Autowired
    private ZaloService zaloService;

    @GetMapping("/auth")
    public void authenticateZaloUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl = "http://your-app.com/zalo/callback";
        String authUrl = zaloService.buildAuthorizationUrl(redirectUrl);
        response.sendRedirect(authUrl);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handleZaloCallback(@RequestParam("code") String code) {
        ZaloAccessTokenResponse accessTokenResponse = zaloService.getAccessToken(code);
        String accessToken = accessTokenResponse.getAccessToken();

        // Lấy thông tin người dùng từ Zalo API (ví dụ: tên, số điện thoại, v.v.)
        ZaloUserProfileResponse userProfileResponse = zaloService.getUserProfile(accessToken);
        String name = userProfileResponse.getName();
        String phoneNumber = userProfileResponse.getPhoneNumber();

        // Gửi tin nhắn đến người dùng qua Zalo API (ví dụ: sử dụng Zalo Notification Service - ZNS)
        ZaloMessageRequestDTO messageRequest = new ZaloMessageRequestDTO();
        messageRequest.setRecipientId(phoneNumber);
        messageRequest.setMessage("Xin chào, " + name + "!");

        zaloService.sendZaloMessage(accessToken, messageRequest);

        return ResponseEntity.ok("Tin nhắn đã được gửi thành công");
    }
}
