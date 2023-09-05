package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.DotThucTap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DotThucTapRepository extends JpaRepository<DotThucTap, Integer> {
}
