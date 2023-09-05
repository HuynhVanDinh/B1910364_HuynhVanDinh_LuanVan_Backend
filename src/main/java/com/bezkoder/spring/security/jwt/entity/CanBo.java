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
@Table(name = "canbo")
public class CanBo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCB")
    private Integer maCB;

    @Column(name = "TenCB", nullable = false)
    private String tenCB;

    @Column(name = "GTinh", nullable = false)
    private String gTinh;

    @Column(name = "NgSinh", nullable = false)
    private LocalDate ngSinh;

    @Column(name = "sdtCB", nullable = false)
    private String sdtCB;

    @ManyToOne
    @JoinColumn(name = "dvtt_id", referencedColumnName = "MaDVTT")
    private DonViThucTap donViThucTap;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    public CanBo(String tenCB, String gTinh, LocalDate ngSinh, String sdtCB, DonViThucTap donViThucTap){
        this.tenCB = tenCB;
        this.gTinh = gTinh;
        this.ngSinh = ngSinh;
        this.sdtCB = sdtCB;
        this.donViThucTap = donViThucTap;
    }
}
