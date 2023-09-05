package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.Khoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhoaRepository extends JpaRepository<Khoa, Integer> {
}
