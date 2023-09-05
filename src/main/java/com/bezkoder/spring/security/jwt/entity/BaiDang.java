package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "baidang")
public class BaiDang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBD")
    private Integer maBD;

    @Column(name = "NoiDung", nullable = false)
    private String noiDung;

    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong;

    @Column(name = "TroCap")
    private int troCap;

    @Column(name = "NgayDang", nullable = false)
    private LocalDate ngayDang;

    @Column(name = "NgaySua", nullable = false)
    private LocalDate ngaySua;

    @OneToOne
    @JoinColumn(name = "dvtt_id", referencedColumnName = "MaDVTT")
    private DonViThucTap donViThucTap;
}
