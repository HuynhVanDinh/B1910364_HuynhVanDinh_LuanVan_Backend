package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.BaiDang;
import com.bezkoder.spring.security.jwt.entity.DangKy;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DangKyRepository extends JpaRepository<DangKy, Integer> {
    long countBySinhVienAndBaiDang(SinhVien sinhVien, BaiDang baiDang);
}
