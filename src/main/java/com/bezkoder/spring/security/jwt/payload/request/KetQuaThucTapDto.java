package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Data;


@Data
public class KetQuaThucTapDto {
    private Integer maKqtt;
    private Float diem;
    private Integer trangThai;
}
