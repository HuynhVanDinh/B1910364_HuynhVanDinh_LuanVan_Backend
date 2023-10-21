package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diemcanbo")
public class DiemCanBo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDiemCB")
    private Integer maDiemCB;
    @Column(name = "diemCB")
    private Float diemCB;
    @ManyToOne
    @JoinColumn(name = "MaSV", referencedColumnName = "MaSV")
    private SinhVien sinhVien;
    @ManyToOne
    @JoinColumn(name = "MaCB", referencedColumnName = "MaCB")
    private CanBo canBo;
    @ManyToOne
    @JoinColumn(name = "MaPDCB", referencedColumnName = "MaPDCB")
    private PhieuDiemCanbo phieuDiemCanbo;

    public DiemCanBo(Float diemCB, SinhVien sinhVien, CanBo canBo, PhieuDiemCanbo phieuDiemCanbo){
        this.diemCB = diemCB;
        this.sinhVien = sinhVien;
        this.canBo = canBo;
        this.phieuDiemCanbo = phieuDiemCanbo;
    }
}
