package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.PhieuDiemGiangvien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuDiemGiangVienRepository extends JpaRepository<PhieuDiemGiangvien, Integer> {
    List<PhieuDiemGiangvien> findByKhoa(Khoa khoaId);
}
