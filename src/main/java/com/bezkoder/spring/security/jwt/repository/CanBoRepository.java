package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.CanBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanBoRepository extends JpaRepository<CanBo, Integer> {
}
