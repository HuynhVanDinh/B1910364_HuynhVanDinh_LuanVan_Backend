package com.bezkoder.spring.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data

@AllArgsConstructor
@Entity
@Table(name = "khoa")
public class Khoa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "khoa_id")
    private Integer khoaId;

    @Column(name = "khoa_name", nullable = false)
    private String khoaName;

    @Column(name = "khoa_sdt", nullable = false)
    private String khoaSdt;

    @Column(name = "khoa_status", nullable = false)
    private Integer khoaStatus = 1;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

//    @OneToMany(mappedBy = "khoa", cascade = CascadeType.ALL)
//    private Set<Lop> danhSachLop = new HashSet<>();

    public Khoa() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    public Khoa(String khoaName, String khoaSdt, Integer khoaStatus) {
        this.khoaName = khoaName;
        this.khoaSdt = khoaSdt;
        this.khoaStatus = khoaStatus;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
