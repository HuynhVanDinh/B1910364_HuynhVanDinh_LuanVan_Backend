package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.BaiDang;
import com.bezkoder.spring.security.jwt.entity.DangKy;
import com.bezkoder.spring.security.jwt.entity.DonViThucTap;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DangKyRepository extends JpaRepository<DangKy, Integer> {
    long countBySinhVienAndBaiDang(SinhVien sinhVien, BaiDang baiDang);
    boolean existsBySinhVienAndBaiDang(SinhVien maSV, BaiDang baiDangId);

    List<DangKy> findBySinhVien(SinhVien sinhVienId);
    List<DangKy> findByBaiDangDonViThucTapMaDvtt(Integer maDvtt);
}
