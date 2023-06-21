package com.example.debt_be.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZaloUserProfileResponse {
    private String id;
    private String name;
    private String gender;
    private String birthday;
    private String avatar;
    private String phoneNumber;
}
