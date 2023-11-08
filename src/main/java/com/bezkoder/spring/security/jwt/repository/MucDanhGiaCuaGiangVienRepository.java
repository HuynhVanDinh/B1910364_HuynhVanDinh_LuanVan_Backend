package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.MucDanhGiaCuaGiangVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MucDanhGiaCuaGiangVienRepository extends JpaRepository<MucDanhGiaCuaGiangVien, Integer> {
    List<MucDanhGiaCuaGiangVien> findMucDanhGiaCuaGiangVienByKhoa(Khoa khoa);
}
