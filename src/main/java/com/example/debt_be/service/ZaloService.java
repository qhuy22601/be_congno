//package com.example.debt_be.service;
//
//import com.example.debt_be.entity.model.ZaloMessageRequest;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.RequestEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.net.URI;
//
//@Service
//public class ZaloService {
//    private final String apiUrl = "https://openapi.zalo.me/v2.0/oa/message";
//
//    @Value("${zalo.appId}")
//    private String appId;
//
//    @Value("${zalo.appSecret}")
//    private String appSecret;
//
//    private final String accessToken= "QPmzKkHHgm5gc3vGurMh1cQSC57eJjv6RO48RfCPysj7kW5RYIVIKsMtHGQz7gq9HRfF5BiLeHrqdsGuXJIpBGc3Lm7sBeSnUE0T3xDToJbgbICMvrd9HXll0aJBVPTDG_1X4hnMfZbmxLSM_K2L8GRrVX_UFku8DPel6hWgmZfTxrKsbsQO8b2eRm-N8Rek4wTD0yyKXLGHeMLzvn_oIsscOtsR3unxGAjrTem1ic97jLf8l2ULQ2oH0d-aFF1SI9CL0x9SXXTYx4eygK3G0dNIE2YZPj8UUM6z_ADdu4wi00";
//    public void sendMessage(String recipient, String message) {
////        RestTemplate restTemplate = new RestTemplate();
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
////        headers.set("access_token", accessToken);
//////        headers.setContentType(MediaType.APPLICATION_JSON);
//////        headers.setBasicAuth(appId, appSecret);
////        ZaloMessageRequest requestBody = new ZaloMessageRequest();
////        requestBody.setPhoneNumber(recipient);
////        requestBody.setMessage(message);
////
////        RequestEntity<ZaloMessageRequest> requestEntity = new RequestEntity<>(
////                requestBody,
////                headers,
////                HttpMethod.POST,
////                URI.create(apiUrl)
////        );
////
////        restTemplate.exchange(requestEntity, Void.class);
//        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        String requestBody = "{\"recipient\":{\"user_id\":\"" + phoneNumber + "\"},\"message\":{\"text\":\"" + message + "\"}}";
//
//        Request request = new Request.Builder()
//                .url(apiUrl)
//                .post(RequestBody.create(mediaType, requestBody))
//                .addHeader("AppId", appId)
//                .addHeader("AppSecret", appSecret)
//                .addHeader("Content-Type", "application/json")
//                .build();
//
//        client.newCall(request).execute();
//    }
//}
