package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiemCanBoRepository extends JpaRepository<DiemCanBo, Integer> {
    List<DiemCanBo> findDiemCanBoByCanBo(CanBo canBo);
    List<DiemCanBo> findDiemCanBoBySinhVien(SinhVien sinhVien);

    DiemCanBo findDiemCanBoByPhieuDiemCanboAndSinhVien(PhieuDiemCanbo phieuDiemCanbo, SinhVien sinhVien);
}
