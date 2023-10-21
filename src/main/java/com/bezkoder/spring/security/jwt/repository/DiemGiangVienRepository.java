package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.DiemGiangVien;
import com.bezkoder.spring.security.jwt.entity.GiangVien;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiemGiangVienRepository extends JpaRepository<DiemGiangVien, Integer> {
    List<DiemGiangVien> findDiemGiangVienByGiangVien(GiangVien giangVien);
    List<DiemGiangVien> findDiemGiangVienBySinhVien(SinhVien sinhVien);
}
