package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ketquathuctap")
public class KetQuaThucTap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKqtt")
    private Integer maKqtt;
    @ManyToOne
    @JoinColumn(name = "MaSV", referencedColumnName = "MaSV")
    private SinhVien sinhVien;
    @ManyToOne
    @JoinColumn(name = "MaDvtt", referencedColumnName = "MaDvtt")
    private DonViThucTap donViThucTap;
    @ManyToOne
    @JoinColumn(name = "MaGV", referencedColumnName = "MaGV")
    private GiangVien giangVien;
    @ManyToOne
    @JoinColumn(name = "MaCB", referencedColumnName = "MaCB")
    private CanBo canBoHuongDan;
    @ManyToOne
    @JoinColumn(name = "MaDot", referencedColumnName = "MaDot")
    private DotThucTap dotThucTap;
    @Column(name = "Diem")
    private Float diem;
    @Column(name = "TrangThai")
    private Integer trangThai;

    public KetQuaThucTap( SinhVien sinhVien, DonViThucTap donViThucTap, GiangVien giangVien, DotThucTap dotThucTap ){

        this.sinhVien = sinhVien;
        this.donViThucTap = donViThucTap;
        this.giangVien = giangVien;
        this.dotThucTap = dotThucTap;
    }
}
