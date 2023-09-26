package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.DonViThucTap;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonViThucTapRepository extends JpaRepository<DonViThucTap,Integer> {
    List<DonViThucTap> findByTenDvttContainingIgnoreCase(String tenDvtt);
}
