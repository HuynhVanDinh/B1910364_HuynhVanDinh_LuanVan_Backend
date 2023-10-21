package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.DotThucTap;
import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.payload.request.DotThucTapDto;
import com.bezkoder.spring.security.jwt.repository.DotThucTapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class DotThucTapService {

    @Autowired
    private DotThucTapRepository dotThucTapRepository;

    public List<DotThucTap> getAllDotThucTap() {
        return dotThucTapRepository.findAll();
    }

    public DotThucTap getDotThucTapById(Integer id) {
        return dotThucTapRepository.findById(id).orElse(null);
    }

    @Transactional
    public DotThucTap createDotThucTap(DotThucTapDto dotThucTapDto) {
        DotThucTap dotThucTap = new DotThucTap();
        dotThucTap.setTenDot(dotThucTapDto.getTenDot());
        dotThucTap.setThoiGianBatDau(dotThucTapDto.getThoiGianBatDau());
        dotThucTap.setThoiGianKetThuc(dotThucTapDto.getThoiGianKetThuc());

        return dotThucTapRepository.save(dotThucTap);
    }

    @Transactional
    public DotThucTap updateDotThucTap(Integer id, DotThucTapDto dotThucTapDto) {
        DotThucTap existingDotThucTap = getDotThucTapById(id);
        if (existingDotThucTap != null) {
            existingDotThucTap.setTenDot(dotThucTapDto.getTenDot());
            existingDotThucTap.setThoiGianBatDau(dotThucTapDto.getThoiGianBatDau());
            existingDotThucTap.setThoiGianKetThuc(dotThucTapDto.getThoiGianKetThuc());
            return dotThucTapRepository.save(existingDotThucTap);
        }
        return null;
    }

    @Transactional
    public void deleteDotThucTap(Integer id) {
        DotThucTap existingDotThucTap = getDotThucTapById(id);
        if (existingDotThucTap != null) {
            dotThucTapRepository.delete(existingDotThucTap);
        }
    }
    @Transactional
    public List<DotThucTap> searchDotThucTapByName(String tenDot, LocalDate thoiGianBatDau, LocalDate thoiGianKetThuc) {
        if (tenDot != null) {
            if (thoiGianBatDau != null && thoiGianKetThuc != null) {
                return dotThucTapRepository.findDotThucTapByTenDotContainingIgnoreCaseAndThoiGianBatDauAndThoiGianKetThuc(tenDot, thoiGianBatDau, thoiGianKetThuc);
            } else if (thoiGianBatDau != null) {
                // Search by tenDot and thoiGianBatDau
                return dotThucTapRepository.findDotThucTapByTenDotContainingIgnoreCaseAndThoiGianBatDau(tenDot, thoiGianBatDau);
            } else if (thoiGianKetThuc != null) {
                // Search by tenDot and thoiGianKetThuc
                return dotThucTapRepository.findDotThucTapByTenDotContainingIgnoreCaseAndThoiGianKetThuc(tenDot, thoiGianKetThuc);
            } else {
                // Search by tenDot only
                return dotThucTapRepository.findDotThucTapByTenDotContainingIgnoreCase(tenDot);
            }
        } else if (thoiGianBatDau != null && thoiGianKetThuc != null) {
            // Search by thoiGianBatDau and thoiGianKetThuc
            return dotThucTapRepository.findDotThucTapByThoiGianBatDauAndThoiGianKetThuc(thoiGianBatDau, thoiGianKetThuc);
        } else if (thoiGianBatDau != null) {
            // Search by thoiGianBatDau only
            return dotThucTapRepository.findDotThucTapByThoiGianBatDau(thoiGianBatDau);
        } else if (thoiGianKetThuc != null) {
            // Search by thoiGianKetThuc only
            return dotThucTapRepository.findDotThucTapByThoiGianKetThuc(thoiGianKetThuc);
        }

        // If no parameters provided, return all dots
        return dotThucTapRepository.findAll();
//        return  null;
    }

}
