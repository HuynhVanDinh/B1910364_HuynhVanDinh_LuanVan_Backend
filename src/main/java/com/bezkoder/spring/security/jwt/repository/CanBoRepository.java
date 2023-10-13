package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.CanBo;
import com.bezkoder.spring.security.jwt.entity.DonViThucTap;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CanBoRepository extends JpaRepository<CanBo, Integer> {
    Optional<CanBo> findByAccountId(Integer accountId);
    List<CanBo> findByDonViThucTap(DonViThucTap donViThucTap);
}
