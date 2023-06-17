package com.example.debt_be.entity.dto;

public class ZaloAccessTokenRequest {
    private String appId;
    private String appSecret;
//    private String code;

    public ZaloAccessTokenRequest() {
    }

    public ZaloAccessTokenRequest(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }


}
