package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.ThoiGianDangKy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThoiGianDangKyRepository extends JpaRepository<ThoiGianDangKy, Integer> {

    ThoiGianDangKy findByKhoa(Khoa khoa);
}
