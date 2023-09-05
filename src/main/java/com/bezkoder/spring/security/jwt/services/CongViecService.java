package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.CongViecDto;
import com.bezkoder.spring.security.jwt.repository.CanBoRepository;
import com.bezkoder.spring.security.jwt.repository.CongViecRepository;
import com.bezkoder.spring.security.jwt.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongViecService {

    private final CongViecRepository congViecRepository;
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private CanBoRepository canBoRepository;
    @Autowired
    public CongViecService(CongViecRepository congViecRepository) {
        this.congViecRepository = congViecRepository;
    }

    public List<CongViec> getAllCongViec() {
        return congViecRepository.findAll();
    }

    public CongViec getCongViecById(Integer id) {
        return congViecRepository.findById(id).orElse(null);
    }

    public CongViec createCongViec(CongViecDto congViecDto, Integer sinhVienId, Integer canBoId) {
        SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).orElse(null);
        CanBo canBo = canBoRepository.findById(canBoId).orElse(null);
        if (sinhVien == null || canBo == null) {
            return null;
        }
        CongViec congViec = new CongViec(
        congViecDto.getMota(),
        congViecDto.getTienDo(),
        congViecDto.getNgayBatDau(),
        congViecDto.getNgayKetThuc(),
        sinhVien,
        canBo
        );
        congViec.setTienDo(0);
        CongViec saveCongViec = congViecRepository.save(congViec);
        return congViecRepository.save(congViec);
    }

    public CongViec updateCongViec(Integer congViecId, CongViecDto congViecDto, Integer sinhVienId, Integer canBoId) {
        CongViec existingCongViec = getCongViecById(congViecId);
        SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).orElse(null);
        CanBo canBo = canBoRepository.findById(canBoId).orElse(null);
        if (sinhVien == null || canBo == null) {
            return null;
        }
        if (existingCongViec != null) {
            existingCongViec.setMota(congViecDto.getMota());
            existingCongViec.setTienDo(congViecDto.getTienDo());
            existingCongViec.setNgayBatDau(congViecDto.getNgayBatDau());
            existingCongViec.setNgayKetThuc(congViecDto.getNgayKetThuc());
            existingCongViec.setSinhVien(sinhVien);
            existingCongViec.setCanBo(canBo);

            return congViecRepository.save(existingCongViec);
        }
        return null;
    }

    public void deleteCongViec(Integer congViecId) {
        CongViec existingCongViec = getCongViecById(congViecId);
        if (existingCongViec != null) {
            congViecRepository.delete(existingCongViec);
        }
    }
}

