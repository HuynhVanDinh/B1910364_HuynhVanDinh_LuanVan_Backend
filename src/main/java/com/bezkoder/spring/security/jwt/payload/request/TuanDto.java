package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TuanDto {
    private String ten_tuan;
    private LocalDate batdau;
    private LocalDate hethan;
}
