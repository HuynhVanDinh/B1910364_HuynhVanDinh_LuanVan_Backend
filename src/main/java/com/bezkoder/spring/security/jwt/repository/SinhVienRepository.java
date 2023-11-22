package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.Lop;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien,Integer> {
    List<SinhVien> findByTenSVContainingIgnoreCase(String tenSV);
    Optional<SinhVien> findByAccountId(Integer accountId);
    List<SinhVien> findSinhVienByLop(Lop lop);
}
