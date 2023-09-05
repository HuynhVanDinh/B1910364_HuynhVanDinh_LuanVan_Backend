package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SinhVienDto {
    private String tenSV;
    private String gioiTinh;
    private LocalDate ngaySinh;
    private String queQuan;
    private Integer lopId;
    private Integer accountId;
}
