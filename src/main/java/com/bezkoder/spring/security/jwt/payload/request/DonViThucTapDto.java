package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Data;

@Data
public class DonViThucTapDto {
    private String tenDvtt;
    private String diaChi;
    private String soDt;
    private  Integer isKhoa;
}
