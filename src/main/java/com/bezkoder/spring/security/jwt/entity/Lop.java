package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data

@AllArgsConstructor
@Entity
@Table(name = "lop")
public class Lop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lop_id")
    private Integer lopId;

    @Column(name = "ten_lop", nullable = false)
    private String tenLop;

    @ManyToOne
    @JoinColumn(name = "khoa_id", referencedColumnName = "khoa_id")
    private Khoa khoa;

    public Lop() {
    }

    public Lop(String tenLop, Khoa khoa) {
        this.tenLop = tenLop;
        this.khoa = khoa;
    }
    public Integer getId(){
        return lopId;
    }

    public String getTen(){
        return tenLop;
    }

}
