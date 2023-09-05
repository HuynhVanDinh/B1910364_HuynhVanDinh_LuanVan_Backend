package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dangky")
public class DangKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDK")
    private Integer maDK;

    @Column(name = "bangDiem", nullable = false)
    private String bangDiem;

    @Column(name = "cv", nullable = false)
    private String sv;

    @Column(name = "trangThai", nullable = false)
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "ma_bd", referencedColumnName = "MaBD")
    private BaiDang baiDang;

    @ManyToOne
    @JoinColumn(name = "ma_sv", referencedColumnName = "MaSV")
    private SinhVien sinhVien;

    public DangKy(String bangDiem, String sv, BaiDang baiDang, SinhVien sinhVien){
        this.baiDang = baiDang;
        this.sv = sv;
        this.trangThai =  1;
        this.bangDiem = bangDiem;
        this.sinhVien = sinhVien;
    }

}
