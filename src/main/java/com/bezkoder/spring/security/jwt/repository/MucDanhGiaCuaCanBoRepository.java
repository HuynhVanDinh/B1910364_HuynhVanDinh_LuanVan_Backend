package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.MucDanhGiaCuaCanBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MucDanhGiaCuaCanBoRepository extends JpaRepository<MucDanhGiaCuaCanBo, Integer> {
}
