package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Data;

@Data
public class DangKyDto {
    private String bangDiem;

    private String sv;

    private Integer trangThai;
}
