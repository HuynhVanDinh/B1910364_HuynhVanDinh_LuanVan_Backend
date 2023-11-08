package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuDiemGiangVienRepository extends JpaRepository<PhieuDiemGiangvien, Integer> {
    List<PhieuDiemGiangvien> findPhieuDiemGiangvienByKhoa(Khoa khoaId);
    List<PhieuDiemGiangvien> findByMucDG(MucDanhGiaCuaGiangVien mucDanhGiaCuaGiangVien);
}
