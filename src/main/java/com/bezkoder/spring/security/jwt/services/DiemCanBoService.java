package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.DiemCanBoDto;
import com.bezkoder.spring.security.jwt.payload.request.DiemGiangVienDto;
import com.bezkoder.spring.security.jwt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiemCanBoService {
    private final DiemCanBoRepository diemCanBoRepository;
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private CanBoRepository canBoRepository;
    @Autowired
    private PhieuDiemCanboRepository phieuDiemCanBoRepository;
    @Autowired
    public DiemCanBoService(DiemCanBoRepository diemCanBoRepository){
        this.diemCanBoRepository = diemCanBoRepository;
    }
    public List<DiemCanBo> getAllDiem() {
        return diemCanBoRepository.findAll();
    }
    public List<DiemCanBo> getAllDiemByCanBo(Integer maCB) {
        CanBo canBo = canBoRepository.findById(maCB).orElse(null);
        return diemCanBoRepository.findDiemCanBoByCanBo(canBo);
    }
    public List<DiemCanBo> getAllDiemSinhvien(Integer sinhVienId) {
        return diemCanBoRepository.findDiemCanBoBySinhVien( sinhVienRepository.findById(sinhVienId).orElse(null));
    }
    public DiemCanBo getDiemCanBoById(Integer id) {
        return diemCanBoRepository.findById(id).orElse(null);
    }
    public DiemCanBo createDiemCanBo(DiemCanBoDto diemCanBoDto, Integer phieuDiemCB, Integer sinhVien, Integer canBo) {
        Optional<PhieuDiemCanbo> phieuDiemCanBoOptional = phieuDiemCanBoRepository.findById(phieuDiemCB);
        Optional<SinhVien> sinhVienOptional = sinhVienRepository.findById(sinhVien);
        Optional<CanBo> canBoOptional = canBoRepository.findById(canBo);
        if(sinhVienOptional.isPresent() || phieuDiemCanBoOptional.isPresent() || canBoOptional.isPresent()){
            SinhVien sinhVienId = sinhVienOptional.get();
            CanBo maCB = canBoOptional.get();
            PhieuDiemCanbo maPhieu = phieuDiemCanBoOptional.get();
            DiemCanBo diemCanBo = new DiemCanBo(
                    diemCanBoDto.getDiemCB(),
                    sinhVienId,
                    maCB,
                    maPhieu
            );
            return diemCanBoRepository.save(diemCanBo);
        }else {
            return null;
        }
    }


    public DiemCanBo updateDiemCanBo(Integer Id_DiemCB, DiemCanBoDto diemCanBoDto) {
        DiemCanBo existingDiemCanBo = getDiemCanBoById(Id_DiemCB);
        if (existingDiemCanBo != null) {
            existingDiemCanBo.setDiemCB(diemCanBoDto.getDiemCB());
            return diemCanBoRepository.save(existingDiemCanBo);
        }
        return null;
    }

    public void deleteDiemCanBo(Integer Id_DiemCB) {
        DiemCanBo existingDiemCanBo = getDiemCanBoById(Id_DiemCB);
        if (existingDiemCanBo != null) {
            diemCanBoRepository.delete(existingDiemCanBo);
        }
    }
}
