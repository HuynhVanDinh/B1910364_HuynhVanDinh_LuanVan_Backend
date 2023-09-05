package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.DonViThucTap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonViThucTapRepository extends JpaRepository<DonViThucTap,Integer> {
}
