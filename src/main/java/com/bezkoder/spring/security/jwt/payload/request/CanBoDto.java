package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CanBoDto {
    private String tenCB;
    private String hinhAnh;
    private String gioiTinh;
    private LocalDate ngSinh;
    private String sdtCB;
}
