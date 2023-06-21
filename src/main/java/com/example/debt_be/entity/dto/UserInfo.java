package com.example.debt_be.entity.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserInfo {
    private Integer id;

    @Nullable
    private String logo;

    private String mst;

    private String name;

    private LocalDate foundatingDate;

    private String city;

    private String district;

    private String ward;

    private String address;

    private String contactName;

    private String contactTitle;

    private String contactNumber;

    private String contactEmail;
}
