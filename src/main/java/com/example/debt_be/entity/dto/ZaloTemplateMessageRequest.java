package com.example.debt_be.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZaloTemplateMessageRequest {
    private String recipientId;
    private String templateId;
    private String params;
}