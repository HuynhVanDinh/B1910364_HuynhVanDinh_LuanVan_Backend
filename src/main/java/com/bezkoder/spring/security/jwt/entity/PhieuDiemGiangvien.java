package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phieudiemgiangvien")
public class PhieuDiemGiangvien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPDGV")
    private Integer maPDGV;
    @Column(name = "noiDungPD")
    private String noiDungPDGV;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "khoaId")
    private Khoa khoa;

    public PhieuDiemGiangvien(String noiDungPD){
        this.noiDungPDGV = noiDungPDGV;
    }
}
