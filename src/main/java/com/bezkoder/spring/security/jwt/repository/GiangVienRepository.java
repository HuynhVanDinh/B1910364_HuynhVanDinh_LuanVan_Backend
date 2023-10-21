package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.GiangVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, Integer> {
    Optional<GiangVien> findByAccountId(Integer accountId);
}
