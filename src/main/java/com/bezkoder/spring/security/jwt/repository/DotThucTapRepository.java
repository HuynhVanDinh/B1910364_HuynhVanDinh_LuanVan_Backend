package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.DotThucTap;
import com.bezkoder.spring.security.jwt.entity.Khoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface DotThucTapRepository extends JpaRepository<DotThucTap, Integer> {
    List<DotThucTap> findDotThucTapByTenDotContainingIgnoreCaseOrThoiGianBatDauOrThoiGianKetThuc(String tenDot, LocalDate thoiGianBatDau, LocalDate thoiGianKetThuc);
    List<DotThucTap> findDotThucTapByTenDotContainingIgnoreCaseAndThoiGianBatDauAndThoiGianKetThuc(String tenDot, LocalDate thoiGianBatDau, LocalDate thoiGianKetThuc);
    List<DotThucTap> findDotThucTapByTenDotContainingIgnoreCaseAndThoiGianBatDau(String tenDot, LocalDate thoiGianBatDau);
    List<DotThucTap> findDotThucTapByTenDotContainingIgnoreCaseAndThoiGianKetThuc(String tenDot,LocalDate thoiGianKetThuc);
    List<DotThucTap> findDotThucTapByTenDotContainingIgnoreCase(String tenDot);
    List<DotThucTap> findDotThucTapByThoiGianBatDauAndThoiGianKetThuc(LocalDate thoiGianBatDau, LocalDate thoiGianKetThuc);
    List<DotThucTap> findDotThucTapByThoiGianBatDau(LocalDate thoiGianBatDau);
    List<DotThucTap> findDotThucTapByThoiGianKetThuc(LocalDate thoiGianKetThuc);
}
