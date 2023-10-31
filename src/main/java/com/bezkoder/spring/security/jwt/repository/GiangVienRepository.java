package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.GiangVien;
import com.bezkoder.spring.security.jwt.entity.Khoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, Integer> {
    Optional<GiangVien> findByAccountId(Integer accountId);

    List<GiangVien> findByKhoa(Khoa khoaId);
}
