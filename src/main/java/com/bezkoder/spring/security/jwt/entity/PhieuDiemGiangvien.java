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
    @Column(name = "diemtoida")
    private Float diemMax;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "khoaId")
    private Khoa khoa;
    @ManyToOne
    @JoinColumn(name = "muc_id", referencedColumnName = "muc_id")
    private MucDanhGiaCuaGiangVien mucDG;
    public PhieuDiemGiangvien(String noiDungPDGV, Float diemMax,Khoa khoa, MucDanhGiaCuaGiangVien mucDG){
        this.noiDungPDGV = noiDungPDGV;
        this.diemMax = diemMax;
        this.khoa = khoa;
        this.mucDG = mucDG;
    }
}
