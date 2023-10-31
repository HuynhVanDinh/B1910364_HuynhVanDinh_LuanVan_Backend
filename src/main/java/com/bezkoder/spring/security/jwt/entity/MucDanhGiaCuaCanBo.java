package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mucdanhgiacuacanbo")
public class MucDanhGiaCuaCanBo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "muc_id")
    private Integer mucId;

    @Column(name = "ten_muc", nullable = false)
    private String tenMuc;

    public MucDanhGiaCuaCanBo(String tenMuc){
        this.tenMuc = tenMuc;
    }
}
