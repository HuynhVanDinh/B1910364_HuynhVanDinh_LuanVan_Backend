package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhoaRepository extends JpaRepository<Khoa, Integer> {
    List<Khoa> findByKhoaNameContainingIgnoreCase(String khoaName);
}
