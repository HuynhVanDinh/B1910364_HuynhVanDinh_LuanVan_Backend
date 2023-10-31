package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.CongViecDto;
import com.bezkoder.spring.security.jwt.repository.CanBoRepository;
import com.bezkoder.spring.security.jwt.repository.CongViecRepository;
import com.bezkoder.spring.security.jwt.repository.SinhVienRepository;
import com.bezkoder.spring.security.jwt.repository.TuanRepository;
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
    private TuanRepository tuanRepository;
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

    public List<CongViec> getCongViecBySinhVienAndCanBoAndTuan(Integer sinhVienId, Integer canBoId, Integer id_tuan){
        SinhVien sinhvien = sinhVienRepository.findById(sinhVienId).orElse(null);
        CanBo canbo = canBoRepository.findById(canBoId).orElse(null);
        Tuan tuan = tuanRepository.findById(id_tuan).orElse(null);
        return congViecRepository.findCongViecBySinhVienAndCanBoAndTuan(sinhvien,canbo,tuan);
    }
    public List<CongViec> getCongViecBySinhVienAndTuan(Integer sinhVienId, Integer id_tuan){
        SinhVien sinhvien = sinhVienRepository.findById(sinhVienId).orElse(null);
        Tuan tuan = tuanRepository.findById(id_tuan).orElse(null);
        return congViecRepository.findCongViecBySinhVienAndTuan(sinhvien,tuan);
    }

    public CongViec createCongViec(CongViecDto congViecDto,Integer tuan, Integer sinhVienId, Integer canBoId) {
        SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).orElse(null);
        CanBo canBo = canBoRepository.findById(canBoId).orElse(null);
        Tuan id_tuan = tuanRepository.findById(tuan).orElse(null);
        if (sinhVien == null || canBo == null || id_tuan == null) {
            return null;
        }
        CongViec congViec = new CongViec(
        congViecDto.getMota(),
        congViecDto.getTienDo(),
        id_tuan,
        sinhVien,
        canBo
        );
        congViec.setTrangThaiCV(0);
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
//            existingCongViec.setTienDo(congViecDto.getTienDo());
//
//            existingCongViec.setSinhVien(sinhVien);
//            existingCongViec.setCanBo(canBo);

            return congViecRepository.save(existingCongViec);
        }
        return null;
    }

    public CongViec updateTienDoCongViec(Integer congViecId, CongViecDto congViecDto, Integer sinhVienId) {
        CongViec existingCongViec = getCongViecById(congViecId);
        SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).orElse(null);
        if (sinhVien == null) {
            return null;
        }
        if (existingCongViec != null) {
//            existingCongViec.setMota(congViecDto.getMota());
            existingCongViec.setTienDo(congViecDto.getTienDo());
            existingCongViec.setTrangThaiCV(1);
//
//            existingCongViec.setSinhVien(sinhVien);
//            existingCongViec.setCanBo(canBo);

            return congViecRepository.save(existingCongViec);
        }
        return null;
    }
    public CongViec duyetCongViec(Integer congViecId, CongViecDto congViecDto) {
        CongViec existingCongViec = getCongViecById(congViecId);

        if (existingCongViec != null) {
            if(congViecDto.getTienDo() == 100){
                existingCongViec.setTrangThaiCV(2);
                return congViecRepository.save(existingCongViec);
            } else {
                existingCongViec.setTrangThaiCV(3);
                return congViecRepository.save(existingCongViec);
            }
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

