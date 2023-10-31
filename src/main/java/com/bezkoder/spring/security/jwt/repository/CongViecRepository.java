package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.CanBo;
import com.bezkoder.spring.security.jwt.entity.CongViec;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import com.bezkoder.spring.security.jwt.entity.Tuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CongViecRepository extends JpaRepository<CongViec, Integer> {

    List<CongViec> findCongViecBySinhVienAndCanBoAndTuan(SinhVien sinhVien, CanBo canBo, Tuan tuan);
    List<CongViec> findCongViecBySinhVienAndTuan(SinhVien sinhVien, Tuan tuan);
}
