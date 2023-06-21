package com.example.debt_be.entity.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ZaloMessageRequest {
    private String phoneNumber;
    private String message;
}
