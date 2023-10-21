package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KetQuaThucTapRepository extends JpaRepository<KetQuaThucTap, Integer> {
    List<KetQuaThucTap> findByDonViThucTap(DonViThucTap madvtt);
    List<KetQuaThucTap> findByDonViThucTapAndCanBoHuongDanIsNull(DonViThucTap madvtt);
    List<KetQuaThucTap> findByGiangVien(Integer magv);
    List<KetQuaThucTap> findByCanBoHuongDan(CanBo macb);
    List<KetQuaThucTap> findByDotThucTap(Integer madot);
    Optional<KetQuaThucTap> findBySinhVien(SinhVien masv);

}
