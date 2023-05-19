package com.example.debt_be.entity.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseDto {
    private String mess;
    private String status;
    private Object payload;
}
