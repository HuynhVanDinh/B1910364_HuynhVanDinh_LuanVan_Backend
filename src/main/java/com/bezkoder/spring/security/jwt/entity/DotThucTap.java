package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
@Table(name = "dotthuctap")
public class DotThucTap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDot")
    private Integer maDot;

    @Column(name = "TenDot", nullable = false)
    private String tenDot;

    @Column(name = "ThoiGianBauDau", nullable = false)
    private LocalDate thoiGianBatDau;

    @Column(name = "ThoiGianKetThuc", nullable = false)
    private LocalDate thoiGianKetThuc;

    public DotThucTap() {
    }

    public DotThucTap(String tenDot, LocalDate thoiGianBatDau,LocalDate thoiGianKetThuc) {
        this.tenDot = tenDot;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
    }
}
