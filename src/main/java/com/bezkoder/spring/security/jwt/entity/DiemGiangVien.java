package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diemgiangvien")
public class DiemGiangVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDiemGV")
    private Integer maDiemGV;
    @Column(name = "diemGV")
    private Float diemGV;
    @ManyToOne
    @JoinColumn(name = "MaSV", referencedColumnName = "MaSV")
    private SinhVien sinhVien;
    @ManyToOne
    @JoinColumn(name = "MaGV", referencedColumnName = "MaGV")
    private GiangVien giangVien;
    @ManyToOne
    @JoinColumn(name = "MaPDGV", referencedColumnName = "MaPDGV")
    private PhieuDiemGiangvien phieuDiemGiangvien;

    public DiemGiangVien(Float diemGV, SinhVien sinhVien, GiangVien giangVien, PhieuDiemGiangvien phieuDiemGiangvien){
        this.diemGV = diemGV;
        this.sinhVien = sinhVien;
        this.giangVien = giangVien;
        this.phieuDiemGiangvien = phieuDiemGiangvien;
    }
}
