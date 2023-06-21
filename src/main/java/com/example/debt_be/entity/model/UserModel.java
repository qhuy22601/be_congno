package com.example.debt_be.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Debt> debt;
}
