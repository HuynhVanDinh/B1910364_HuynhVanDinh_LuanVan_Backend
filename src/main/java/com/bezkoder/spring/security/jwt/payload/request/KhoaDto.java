package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Data;

@Data
public class KhoaDto {
    private Integer khoaId;
    private String khoaName;
    private  String khoaSdt;
    private Integer khoaStatus = 1;
}
