package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.BaiDang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaiDangRepository extends JpaRepository<BaiDang, Integer> {
}
