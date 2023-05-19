package com.example.debt_be.entity.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nullable
    private String logo;

    private String mst;

    private String name;

    @Nullable
    private LocalDate foundatingDate;

    @Nullable
    private String city;

    @Nullable
    private String district;

    @Nullable
    private String ward;

    @Nullable
    private String address;

    @Nullable
    private String contactName;

    @Nullable
    private String contactTitle;

    @Nullable
    private String contactNumber;

    @Nullable
    private String contactEmail;
}