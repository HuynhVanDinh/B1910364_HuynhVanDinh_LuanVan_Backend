package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.CongViec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongViecRepository extends JpaRepository<CongViec, Integer> {
}
