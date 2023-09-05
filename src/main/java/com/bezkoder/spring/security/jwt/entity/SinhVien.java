package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
@Table(name = "sinhvien")
public class SinhVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaSV")
    private Integer maSV;

    @Column(name = "TenSV", nullable = false)
    private String tenSV;

    @Column(name = "GioiTinh", nullable = false)
    private String gioiTinh;

    @Column(name = "NgaySinh", nullable = false)
    private LocalDate ngaySinh;

    @Column(name = "QueQuan", nullable = false)
    private String queQuan;

    @ManyToOne
    @JoinColumn(name = "lop_id", referencedColumnName = "lop_id")
    private Lop lop;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    public SinhVien() {
    }

    public SinhVien(String tenSV, String gioiTinh, LocalDate ngaySinh, String queQuan, Lop lop) {
        this.tenSV = tenSV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.queQuan = queQuan;
        this.lop = lop;
    }
}
