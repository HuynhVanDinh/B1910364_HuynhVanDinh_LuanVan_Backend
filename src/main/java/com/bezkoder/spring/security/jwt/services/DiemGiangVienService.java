package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.DiemGiangVienDto;
import com.bezkoder.spring.security.jwt.repository.DiemGiangVienRepository;
import com.bezkoder.spring.security.jwt.repository.GiangVienRepository;
import com.bezkoder.spring.security.jwt.repository.PhieuDiemGiangVienRepository;
import com.bezkoder.spring.security.jwt.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiemGiangVienService {
    private final DiemGiangVienRepository diemGiangVienRepository;
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private GiangVienRepository giangVienRepository;
    @Autowired
    private PhieuDiemGiangVienRepository phieuDiemGiangVienRepository;
    @Autowired
    public DiemGiangVienService(DiemGiangVienRepository diemGiangVienRepository){
        this.diemGiangVienRepository = diemGiangVienRepository;
    }
    public List<DiemGiangVien> getAllDiem() {
        return diemGiangVienRepository.findAll();
    }
    public List<DiemGiangVien> getAllDiemByGiangVien(Integer maGV) {
        GiangVien giangVien = giangVienRepository.findById(maGV).orElse(null);
        return diemGiangVienRepository.findDiemGiangVienByGiangVien(giangVien);
    }
    public List<DiemGiangVien> getAllDiemSinhvien(Integer sinhVienId) {
        return diemGiangVienRepository.findDiemGiangVienBySinhVien( sinhVienRepository.findById(sinhVienId).orElse(null));
    }
    public DiemGiangVien getDiemGiangVienById(Integer id) {
        return diemGiangVienRepository.findById(id).orElse(null);
    }
    public DiemGiangVien createDiemGiangVien(DiemGiangVienDto diemGiangVienDto, Integer phieuDiemGV, Integer sinhVien, Integer giangVien) {
        Optional<PhieuDiemGiangvien> phieuDiemGiangvienOptional = phieuDiemGiangVienRepository.findById(phieuDiemGV);
        Optional<SinhVien> sinhVienOptional = sinhVienRepository.findById(sinhVien);
        Optional<GiangVien> giangVienOptional = giangVienRepository.findById(giangVien);
        if(sinhVienOptional.isPresent() || phieuDiemGiangvienOptional.isPresent() || giangVienOptional.isPresent()){
            SinhVien sinhVienId = sinhVienOptional.get();
            GiangVien maGV = giangVienOptional.get();
            PhieuDiemGiangvien maPhieu = phieuDiemGiangvienOptional.get();
            DiemGiangVien diemGiangVien = new DiemGiangVien(
                    diemGiangVienDto.getDiemGV(),
                    sinhVienId,
                    maGV,
                    maPhieu
            );
            return diemGiangVienRepository.save(diemGiangVien);
        }else {
            return null;
        }
    }


    public DiemGiangVien updateDiemGiangVien(Integer Id_DiemGV, DiemGiangVienDto diemGiangVienDto) {
        DiemGiangVien existingDiemGiangVien = getDiemGiangVienById(Id_DiemGV);
        if (existingDiemGiangVien != null) {
            existingDiemGiangVien.setDiemGV(diemGiangVienDto.getDiemGV());
            return diemGiangVienRepository.save(existingDiemGiangVien);
        }
        return null;
    }

    public void deleteDiemGiangVien(Integer Id_DiemGV) {
        DiemGiangVien existingDiemGiangVien = getDiemGiangVienById(Id_DiemGV);
        if (existingDiemGiangVien != null) {
            diemGiangVienRepository.delete(existingDiemGiangVien);
        }
    }
}
