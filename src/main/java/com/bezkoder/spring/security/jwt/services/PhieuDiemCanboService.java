package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.DanhGiaDto;
import com.bezkoder.spring.security.jwt.payload.request.PhieuDiemCanboDto;
import com.bezkoder.spring.security.jwt.repository.DanhGiaRepository;
import com.bezkoder.spring.security.jwt.repository.PhieuDiemCanboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhieuDiemCanboService {
    private final PhieuDiemCanboRepository phieuDiemCanboRepository;
    @Autowired
    public PhieuDiemCanboService(PhieuDiemCanboRepository phieuDiemCanboRepository) {
        this.phieuDiemCanboRepository = phieuDiemCanboRepository;
    }
    public List<PhieuDiemCanbo> getAllPhieuDiemCanbo() {
        return phieuDiemCanboRepository.findAll();
    }

    public PhieuDiemCanbo getPhieuDiemCanboById(Integer id) {
        return phieuDiemCanboRepository.findById(id).orElse(null);
    }
    public PhieuDiemCanbo createPhieuDiemCanbo(PhieuDiemCanboDto phieuDiemCanboDto) {
            PhieuDiemCanbo phieuDiemCanbo = new PhieuDiemCanbo(
                    phieuDiemCanboDto.getNoiDungPD()
            );
            return phieuDiemCanboRepository.save(phieuDiemCanbo);

    }

    public PhieuDiemCanbo updatePhieuDiemCanbo(Integer phieuDiemCanboId, PhieuDiemCanboDto phieuDiemCanboDto) {
        PhieuDiemCanbo existingPhieuDiemCanbo = getPhieuDiemCanboById(phieuDiemCanboId);
        if (existingPhieuDiemCanbo != null) {
            existingPhieuDiemCanbo.setNoiDungPD(phieuDiemCanboDto.getNoiDungPD());
            return phieuDiemCanboRepository.save(existingPhieuDiemCanbo);
        }
        return null;
    }

    public void deletePhieuDiemCanbo(Integer phieuDiemCanboId) {
        PhieuDiemCanbo existingPhieuDiemCanbo = getPhieuDiemCanboById(phieuDiemCanboId);
        if (existingPhieuDiemCanbo != null) {
            phieuDiemCanboRepository.delete(existingPhieuDiemCanbo);
        }
    }
}
