package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "donvithuctap")
public class DonViThucTap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDvtt")
    private Integer maDvtt;

    @Column(name = "TenDvtt", nullable = false)
    private String tenDvtt;

    @Column(name = "DiaChi", nullable = false)
    private String diaChi;

    @Column(name = "SoDt", nullable = false)
    private String soDt;

    @Column(name = "isKhoa", nullable = false)
    private Integer isKhoa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    public DonViThucTap() {
    }

    public DonViThucTap(String tenDVtt, String diaChi, String soDt,Integer isKhoa ) {
        this.tenDvtt = tenDVtt;
        this.diaChi = diaChi;
        this.soDt = soDt;
        this.isKhoa = isKhoa;
    }
}
