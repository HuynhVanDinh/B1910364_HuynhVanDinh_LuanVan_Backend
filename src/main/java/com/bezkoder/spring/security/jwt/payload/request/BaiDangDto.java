package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BaiDangDto {
    private String noiDung;
    private Integer soLuong;
    private int troCap;
    private LocalDate ngayDang;
    private LocalDate ngaySua;
}
