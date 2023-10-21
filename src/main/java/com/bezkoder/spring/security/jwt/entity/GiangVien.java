package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "giangvien")
public class GiangVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGV")
    private Integer maGV;
    @Column(name = "TenGV")
    private String tenGV;
    @Column(name = "anhGV")
    private String anhGV;
    @ManyToOne
    @JoinColumn(name = "khoa_id", referencedColumnName = "khoa_id")
    private Khoa khoa;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;
    public GiangVien(String tenGV, String anhGV, Khoa khoa){
        this.tenGV = tenGV;
        this.anhGV = anhGV;
        this.khoa = khoa;
    }
}

