//package com.example.debt_be.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ZaloConfig {
//
//    @Value("${zalo.appId}")
//    private String appId;
//
//    @Value("${zalo.appSecret}")
//    private String appSecret;
//
//    @Bean
//    public ZaloClient zaloClient() {
//        return new ZaloClient(appId, appSecret);
//    }
//}