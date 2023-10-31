package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {
    List<DanhGia> findByTuan(Tuan tuan);
    List<DanhGia> findBySinhVien(SinhVien sinhVien);
    List<DanhGia> findByCanBoHuongDan(CanBo canBo);

    List<DanhGia> findBySinhVienAndCanBoHuongDanAndTuan(SinhVien sinhVien,CanBo canBo, Tuan tuan);
}
