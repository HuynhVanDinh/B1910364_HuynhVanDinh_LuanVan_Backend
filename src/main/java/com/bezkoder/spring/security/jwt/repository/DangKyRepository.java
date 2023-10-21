package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.BaiDang;
import com.bezkoder.spring.security.jwt.entity.DangKy;
import com.bezkoder.spring.security.jwt.entity.DonViThucTap;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DangKyRepository extends JpaRepository<DangKy, Integer> {
    long countBySinhVienAndBaiDang(SinhVien sinhVien, BaiDang baiDang);
    boolean existsBySinhVienAndBaiDang(SinhVien maSV, BaiDang baiDangId);

    List<DangKy> findBySinhVien(SinhVien sinhVienId);

    List<DangKy> findBySinhVienAndTrangThai(SinhVien sinhVienId, Integer trangThai);
    List<DangKy> findByBaiDangDonViThucTapMaDvtt(Integer maDvtt);

    Optional<DangKy> findByBaiDangDonViThucTapMaDvttAndSinhVien(Integer maDvtt, SinhVien sinhvienid);
}
