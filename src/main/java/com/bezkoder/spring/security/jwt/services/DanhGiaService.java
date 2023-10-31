package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.CongViecDto;
import com.bezkoder.spring.security.jwt.payload.request.DangKyDto;
import com.bezkoder.spring.security.jwt.payload.request.DanhGiaDto;
import com.bezkoder.spring.security.jwt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DanhGiaService {
    private final DanhGiaRepository danhGiaRepository;

    @Autowired
    private BaiDangRepository baiDangRepository;
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private CanBoRepository canBoRepository;
    @Autowired
    private TuanRepository tuanRepository;
    @Autowired
    public DanhGiaService(DanhGiaRepository danhGiaRepository) {
        this.danhGiaRepository = danhGiaRepository;
    }

    public List<DanhGia> getAllDanhGia() {
        return danhGiaRepository.findAll();
    }
    public List<DanhGia> getAllDanhGiaByTuan(Integer id_tuan) {
        Tuan tuan = tuanRepository.findById(id_tuan).orElse(null);
        return danhGiaRepository.findByTuan(tuan);
    }
    public List<DanhGia> getDanhGiaBySinhVienAndCanBoAndTuan(Integer sinhVienId, Integer canBoId, Integer id_tuan){
        SinhVien sinhvien = sinhVienRepository.findById(sinhVienId).orElse(null);
        CanBo canbo = canBoRepository.findById(canBoId).orElse(null);
        Tuan tuan = tuanRepository.findById(id_tuan).orElse(null);
        return danhGiaRepository.findBySinhVienAndCanBoHuongDanAndTuan(sinhvien,canbo,tuan);
    }
    public List<DanhGia> getAllDanhGiaSinhVien(Integer sinhVienId) {
        return danhGiaRepository.findBySinhVien( sinhVienRepository.findById(sinhVienId).orElse(null));
    }
    public List<DanhGia> getAllDangGiaCanBo(Integer canBoId) {
        return danhGiaRepository.findByCanBoHuongDan(canBoRepository.findById(canBoId).orElse(null));
    }
    public DanhGia getDanhGiaById(Integer id) {
        return danhGiaRepository.findById(id).orElse(null);
    }

//    public DanhGia createDangKy(DanhGiaDto danhGiaDto, Integer sinhVien, Integer canBo,Integer tuan) {
//        Optional<Tuan> tuanOptional = tuanRepository.findById(tuan);
//        Optional<SinhVien> sinhVienOptional = sinhVienRepository.findById(sinhVien);
//        Optional<CanBo> canBoOptional = canBoRepository.findById(canBo);
//        if(sinhVienOptional.isPresent() || tuanOptional.isPresent() || canBoOptional.isPresent()){
//            SinhVien sinhVienId = sinhVienOptional.get();
//            CanBo canBoId = canBoOptional.get();
//            Tuan id_tuan = tuanOptional.get();
//            DanhGia danhGia = new DanhGia(
//                    danhGiaDto.getNoiDungDG(),
//                    id_tuan,
//                    sinhVienId,
//                    canBoId
//            );
//            return danhGiaRepository.save(danhGia);
//        }else {
//            return null;
//        }
//    }
    public DanhGia createDangKy(DanhGiaDto danhGiaDto, Integer tuan, Integer sinhVienId, Integer canBoId) {
        SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).orElse(null);
        CanBo canBo = canBoRepository.findById(canBoId).orElse(null);
        Tuan id_tuan = tuanRepository.findById(tuan).orElse(null);
        if (sinhVien == null || canBo == null || id_tuan == null) {
            return null;
        }
        DanhGia danhGia = new DanhGia(
                    danhGiaDto.getNoiDungDG(),
                    id_tuan,
                    sinhVien,
                    canBo
            );
        DanhGia savedanhGia = danhGiaRepository.save(danhGia);
        return danhGiaRepository.save(savedanhGia);
    }

    public DanhGia updateDanhGia(Integer danhGiaId, DanhGiaDto danhGiaDto) {
        DanhGia existingDanhgia = getDanhGiaById(danhGiaId);
        if (existingDanhgia != null) {
           existingDanhgia.setNoidungDG(danhGiaDto.getNoiDungDG());
            return danhGiaRepository.save(existingDanhgia);
        }
        return null;
    }

    public void deleteDanhGia(Integer danhGiaId) {
        DanhGia existingDanhGia = getDanhGiaById(danhGiaId);
        if (existingDanhGia != null) {
            danhGiaRepository.delete(existingDanhGia);
        }
    }
}
