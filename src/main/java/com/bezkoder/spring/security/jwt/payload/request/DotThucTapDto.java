package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DotThucTapDto {
    private String tenDot;
    private LocalDate thoiGianBatDau;
    private LocalDate thoiGianKetThuc;
}
