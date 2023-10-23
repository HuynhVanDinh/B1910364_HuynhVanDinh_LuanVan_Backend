package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CongViecDto {
    private String mota;
    private int tienDo;
}
