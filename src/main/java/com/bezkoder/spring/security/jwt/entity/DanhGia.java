package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "danhgia")
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDG")
    private Integer maDG;
    @Column(name = "noidungDG")
    private  String noidungDG;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tuan", referencedColumnName = "id_tuan")
    private Tuan tuan;
    @ManyToOne
    @JoinColumn(name = "MaSV", referencedColumnName = "MaSV")
    private SinhVien sinhVien;
    @ManyToOne
    @JoinColumn(name = "MaCB", referencedColumnName = "MaCB")
    private CanBo canBoHuongDan;

    public DanhGia( String noidungDG, Tuan tuan, SinhVien sinhVien, CanBo canBo){
        this.noidungDG = noidungDG;
        this.tuan = tuan;
        this.sinhVien = sinhVien;
        this.canBoHuongDan = canBo;
    }
}
