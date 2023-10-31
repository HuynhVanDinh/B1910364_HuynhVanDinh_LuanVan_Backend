package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phieudiemcanbo")
public class PhieuDiemCanbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPDCB")
    private Integer maPDCB;
    @Column(name = "noiDungPD")
    private String noiDungPD;

    @ManyToOne
    @JoinColumn(name = "muc_id", referencedColumnName = "muc_id")
    private MucDanhGiaCuaCanBo mucDG;

    public PhieuDiemCanbo(String noiDungPD, MucDanhGiaCuaCanBo mucDG){
        this.noiDungPD = noiDungPD;
        this.mucDG = mucDG;
    }
}
