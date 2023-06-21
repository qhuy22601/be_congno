package com.example.debt_be.entity.dto;

public class ZaloMessageRequestDTO {
    private String recipientId;
    private String message;

    public ZaloMessageRequestDTO() {
    }

    public ZaloMessageRequestDTO(String recipientId, String message) {
        this.recipientId = recipientId;
        this.message = message;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
