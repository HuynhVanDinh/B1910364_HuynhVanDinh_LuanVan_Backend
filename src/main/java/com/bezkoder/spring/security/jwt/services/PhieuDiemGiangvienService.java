package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.PhieuDiemGiangvienDto;
import com.bezkoder.spring.security.jwt.repository.KhoaRepository;
import com.bezkoder.spring.security.jwt.repository.MucDanhGiaCuaCanBoRepository;
import com.bezkoder.spring.security.jwt.repository.MucDanhGiaCuaGiangVienRepository;
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
    private MucDanhGiaCuaGiangVienRepository mucDanhGiaCuaGiangVienRepository;
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
        return phieuDiemGiangVienRepository.findPhieuDiemGiangvienByKhoa(khoa);
    }
    public List<PhieuDiemGiangvien> getPhieuDiemGiangVienByMuc(Integer muc_id) {
        MucDanhGiaCuaGiangVien mucId = mucDanhGiaCuaGiangVienRepository.findById(muc_id).orElse(null);
        return phieuDiemGiangVienRepository.findByMucDG(mucId);
    }
    public PhieuDiemGiangvien createPhieuDiemGiangvien(PhieuDiemGiangvienDto phieuDiemGiangvienDto, Integer khoaId, Integer muc_id) {
        Khoa khoa = khoaRepository.findById(khoaId).orElse(null);
        MucDanhGiaCuaGiangVien mucID = mucDanhGiaCuaGiangVienRepository.findById(muc_id).orElse(null);
        if (khoa == null || mucID == null) {
            return null;

        } else {
            PhieuDiemGiangvien phieuDiemGiangvien = new PhieuDiemGiangvien(
                    phieuDiemGiangvienDto.getNoiDungPDGV(),
                    phieuDiemGiangvienDto.getDiemMax(),
                    khoa,
                    mucID
            );
            return phieuDiemGiangVienRepository.save(phieuDiemGiangvien);
        }


    }

    public PhieuDiemGiangvien updatePhieuDiemGiangvien(Integer phieuDiemGiangvienId, PhieuDiemGiangvienDto phieuDiemGiangvienDto, Integer muc_id) {
        PhieuDiemGiangvien existingPhieuDiemGiangvien = getPhieuDiemGiangvienById(phieuDiemGiangvienId);
        MucDanhGiaCuaGiangVien mucID = mucDanhGiaCuaGiangVienRepository.findById(muc_id).orElse(null);
        if (existingPhieuDiemGiangvien != null) {
            existingPhieuDiemGiangvien.setNoiDungPDGV(phieuDiemGiangvienDto.getNoiDungPDGV());
            existingPhieuDiemGiangvien.setDiemMax(phieuDiemGiangvienDto.getDiemMax());
            existingPhieuDiemGiangvien.setMucDG(mucID);
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
