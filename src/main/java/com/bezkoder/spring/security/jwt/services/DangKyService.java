package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.BaiDang;
import com.bezkoder.spring.security.jwt.entity.DangKy;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import com.bezkoder.spring.security.jwt.payload.request.DangKyDto;
import com.bezkoder.spring.security.jwt.repository.BaiDangRepository;
import com.bezkoder.spring.security.jwt.repository.DangKyRepository;
import com.bezkoder.spring.security.jwt.repository.DonViThucTapRepository;
import com.bezkoder.spring.security.jwt.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DangKyService {

    private final DangKyRepository dangKyRepository;
    @Autowired
    private BaiDangRepository baiDangRepository;
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    public DangKyService(DangKyRepository dangKyRepository) {
        this.dangKyRepository = dangKyRepository;
    }

    public List<DangKy> getAllDangKy() {
        return dangKyRepository.findAll();
    }

    public DangKy getDangKyById(Integer id) {
        return dangKyRepository.findById(id).orElse(null);
    }

    public DangKy createDangKy(DangKyDto dangKyDto, Integer baiDangId, Integer sinhVienId) {
        BaiDang baiDang = baiDangRepository.findById(baiDangId).orElse(null);
        SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).orElse(null);
        if (baiDang == null || sinhVien == null) {
            return null;
        }

        long existingDangKyCount = dangKyRepository.countBySinhVienAndBaiDang(sinhVien, baiDang);
        if (existingDangKyCount > 0) {
            return null;
        }


        DangKy dangKy = new DangKy(
        dangKyDto.getBangDiem(),
        dangKyDto.getSv(),
        baiDang,
        sinhVien
        );

        return dangKyRepository.save(dangKy);
    }


    public DangKy updateDangKy(Integer dangKyId, DangKyDto dangKyDto, Integer baiDangId, Integer sinhVienId) {
        DangKy existingDangKy = getDangKyById(dangKyId);
        if (existingDangKy != null) {
            existingDangKy.setBangDiem(dangKyDto.getBangDiem());
            existingDangKy.setSv(dangKyDto.getSv());

            BaiDang baiDang = new BaiDang();
            baiDang.setMaBD(baiDangId);
            existingDangKy.setBaiDang(baiDang);

            SinhVien sinhVien = new SinhVien();
            sinhVien.setMaSV(sinhVienId);
            existingDangKy.setSinhVien(sinhVien);

            return dangKyRepository.save(existingDangKy);
        }
        return null;
    }

    public void deleteDangKy(Integer dangKyId) {
        DangKy existingDangKy = getDangKyById(dangKyId);
        if (existingDangKy != null) {
            dangKyRepository.delete(existingDangKy);
        }
    }
}

