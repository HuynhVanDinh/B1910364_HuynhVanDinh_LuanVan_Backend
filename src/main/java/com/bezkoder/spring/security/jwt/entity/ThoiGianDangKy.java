package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "thoigiandangky")
public class ThoiGianDangKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tgdk")
    private Integer id_tgdk;
    @Column(name = "tgbd")
    private LocalDate tgbd;
    @Column(name = "tgkt")
    private LocalDate tgkt;
    @Column(name = "ghichu")
    private String ghichu;
    @OneToOne
    @JoinColumn(name = "khoa_Id", referencedColumnName = "khoa_Id")
    private Khoa khoa;
   public ThoiGianDangKy(LocalDate tgbd, LocalDate tgkt, String ghichu, Khoa khoa){
        this.tgbd = tgbd;
        this.tgkt = tgkt;
        this.ghichu = ghichu;
        this.khoa = khoa;
    }
}
