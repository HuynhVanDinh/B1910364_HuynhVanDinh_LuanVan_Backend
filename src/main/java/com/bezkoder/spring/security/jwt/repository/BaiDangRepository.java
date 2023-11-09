package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.BaiDang;
import com.bezkoder.spring.security.jwt.entity.DonViThucTap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaiDangRepository extends JpaRepository<BaiDang, Integer> {

    BaiDang findByDonViThucTap(DonViThucTap donViThucTap);
}
