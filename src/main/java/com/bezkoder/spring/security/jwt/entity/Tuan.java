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
@Table(name = "tuan")
public class Tuan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tuan")
    private Integer id_tuan;
    @Column(name = "ten_tuan")
    private String ten_tuan;
    @Column(name = "batdau")
    private LocalDate batdau;
    @Column(name = "hethan")
    private LocalDate hethan;
    @Column(name = "so_buoi")
    private Integer so_buoi;
    @Column(name = "is_complete")
    private int isComplete;
    @ManyToOne
    @JoinColumn(name = "MaCB", referencedColumnName = "MaCB")
    private CanBo canbo;
    public Tuan(String ten_tuan, LocalDate batdau, LocalDate hethan, Integer so_buoi, CanBo canbo){
        this.ten_tuan = ten_tuan;
        this.batdau = batdau;
        this.hethan = hethan;
        this.so_buoi = so_buoi;
        this.canbo = canbo;
    }
}
