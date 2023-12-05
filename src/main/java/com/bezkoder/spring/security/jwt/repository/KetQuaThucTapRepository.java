package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KetQuaThucTapRepository extends JpaRepository<KetQuaThucTap, Integer> {
    List<KetQuaThucTap> findByDonViThucTap(DonViThucTap madvtt);
    List<KetQuaThucTap> findByDonViThucTapAndCanBoHuongDanIsNull(DonViThucTap madvtt);
    List<KetQuaThucTap> findByGiangVienAndTrangThai(GiangVien magv, int trangThai);
    List<KetQuaThucTap> findByCanBoHuongDanAndTrangThai(CanBo macb, int trangThai);
    List<KetQuaThucTap> findByCanBoHuongDan(CanBo macb);
    List<KetQuaThucTap> findByDotThucTap(DotThucTap madot);
    Optional<KetQuaThucTap> findBySinhVien(SinhVien masv);

//    List<KetQuaThucTap> findKetQuaThucTapBySinhVienTenSVContainingIgnoreCaseOrSinhVienMaSV(String keyword);
@Query("SELECT k FROM KetQuaThucTap k WHERE LOWER(k.sinhVien.tenSV) LIKE %:keyword% OR LOWER(k.sinhVien.maSV) LIKE %:keyword% OR LOWER(k.sinhVien.lop.tenLop) LIKE %:keyword%")
List<KetQuaThucTap> searchKetQuaThucTap(@Param("keyword") String keyword);
    @Query("SELECT k FROM KetQuaThucTap k WHERE " +
            "(LOWER(k.sinhVien.tenSV) LIKE %:keyword% OR " +
            "LOWER(k.sinhVien.maSV) LIKE %:keyword% OR " +
            "LOWER(k.sinhVien.lop.tenLop) LIKE %:keyword%) AND " +
            "k.trangThai = 3 AND " +
            "k.giangVien.maGV = :magv")
    List<KetQuaThucTap> searchKetQuaThucTapByGiangVien(@Param("keyword") String keyword, @Param("magv") int magv);



}
