package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mucdanhgiacuagiangvien")
public class MucDanhGiaCuaGiangVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "muc_id")
    private Integer mucId;

    @Column(name = "ten_muc", nullable = false)
    private String tenMuc;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "khoaId")
    private Khoa khoa;

    public MucDanhGiaCuaGiangVien(String tenMuc, Khoa khoa){
        this.tenMuc = tenMuc;
        this.khoa = khoa;
    }
}
