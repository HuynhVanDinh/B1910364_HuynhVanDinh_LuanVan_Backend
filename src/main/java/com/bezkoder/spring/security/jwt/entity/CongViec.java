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
@Table(name = "congviec")
public class CongViec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCV")
    private Integer maCV;

    @Column(name = "Mota", nullable = false)
    private String mota;

    @Column(name = "TienDo", nullable = false)
    private int tienDo;

    @Column(name = "TrangThaiCV")
    private Integer trangThaiCV;
    @ManyToOne
    @JoinColumn(name = "id_tuan", referencedColumnName = "id_tuan")
    private Tuan tuan;

//    @Column(name = "NgayBatDau", nullable = false)
//    private LocalDate ngayBatDau;
//
//    @Column(name = "NgayKetThuc", nullable = false)
//    private LocalDate ngayKetThuc;

    @ManyToOne
    @JoinColumn(name = "MaSinhVien", referencedColumnName = "MaSV")  // Liên kết với bảng SinhVien qua trường MaSinhVien
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "MaCanBo", referencedColumnName = "MaCB")  // Liên kết với bảng CanBo qua trường MaCanBo
    private CanBo canBo;

    public CongViec(String mota, int tienDo, Tuan tuan,SinhVien sinhVien, CanBo canBo){
        this.mota = mota;
        this.tienDo = tienDo;
//        this.trangThaiCV = trangThaiCV;
        this.tuan = tuan;
//        this.ngayBatDau = ngayBatDau;
//        this.ngayKetThuc = ngayKetThuc;
        this.sinhVien = sinhVien;
        this.canBo = canBo;
    }
}
