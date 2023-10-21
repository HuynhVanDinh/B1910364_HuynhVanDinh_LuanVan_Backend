package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.PhieuDiemGiangvien;
import com.bezkoder.spring.security.jwt.payload.request.PhieuDiemGiangvienDto;
import com.bezkoder.spring.security.jwt.repository.KhoaRepository;
import com.bezkoder.spring.security.jwt.repository.PhieuDiemGiangVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuDiemGiangvienService {
    private final PhieuDiemGiangVienRepository phieuDiemGiangVienRepository;
    @Autowired
    private KhoaRepository khoaRepository;
    @Autowired
    public PhieuDiemGiangvienService(PhieuDiemGiangVienRepository phieuDiemGiangvienRepository) {
        this.phieuDiemGiangVienRepository = phieuDiemGiangvienRepository;
    }
    public List<PhieuDiemGiangvien> getAllPhieuDiemGiangvien() {
        return phieuDiemGiangVienRepository.findAll();
    }

    public PhieuDiemGiangvien getPhieuDiemGiangvienById(Integer id) {
        return phieuDiemGiangVienRepository.findById(id).orElse(null);
    }
    public List<PhieuDiemGiangvien> getPhieuDiemGiangvienByKhoa(Integer khoaId) {
        Khoa khoa = khoaRepository.findById(khoaId).orElse(null);
        return phieuDiemGiangVienRepository.findByKhoa(khoa);
    }
    public PhieuDiemGiangvien createPhieuDiemGiangvien(PhieuDiemGiangvienDto phieuDiemGiangvienDto, Integer khoaId) {
        Khoa khoa = khoaRepository.findById(khoaId).orElse(null);
        if (khoa == null) {
            return null;

        } else {
            PhieuDiemGiangvien phieuDiemGiangvien = new PhieuDiemGiangvien(
                    phieuDiemGiangvienDto.getNoiDungPDGV()
            );
            return phieuDiemGiangVienRepository.save(phieuDiemGiangvien);
        }


    }

    public PhieuDiemGiangvien updatePhieuDiemGiangvien(Integer phieuDiemGiangvienId, PhieuDiemGiangvienDto phieuDiemGiangvienDto) {
        PhieuDiemGiangvien existingPhieuDiemGiangvien = getPhieuDiemGiangvienById(phieuDiemGiangvienId);
        if (existingPhieuDiemGiangvien != null) {
            existingPhieuDiemGiangvien.setNoiDungPDGV(phieuDiemGiangvienDto.getNoiDungPDGV());
            return phieuDiemGiangVienRepository.save(existingPhieuDiemGiangvien);
        }
        return null;
    }

    public void deletePhieuDiemGiangvien(Integer phieuDiemGiangvienId) {
        PhieuDiemGiangvien existingPhieuDiemGiangvien = getPhieuDiemGiangvienById(phieuDiemGiangvienId);
        if (existingPhieuDiemGiangvien != null) {
           phieuDiemGiangVienRepository.delete(existingPhieuDiemGiangvien);
        }
    }
}
