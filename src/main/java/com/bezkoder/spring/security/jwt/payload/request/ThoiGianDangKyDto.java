package com.bezkoder.spring.security.jwt.payload.request;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ThoiGianDangKyDto {
    private LocalDate tgbd;
    private LocalDate tgkt;
    private String ghichu;
}
