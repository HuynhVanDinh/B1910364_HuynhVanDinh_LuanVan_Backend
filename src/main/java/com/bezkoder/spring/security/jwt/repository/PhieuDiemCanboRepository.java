package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.MucDanhGiaCuaCanBo;
import com.bezkoder.spring.security.jwt.entity.PhieuDiemCanbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuDiemCanboRepository extends JpaRepository<PhieuDiemCanbo,Integer> {
    List<PhieuDiemCanbo> findByMucDG(MucDanhGiaCuaCanBo mucDanhGiaCuaCanBo);
}
