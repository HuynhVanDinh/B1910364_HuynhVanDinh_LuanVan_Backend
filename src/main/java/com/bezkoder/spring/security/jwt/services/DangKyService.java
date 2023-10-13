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
    private DonViThucTapRepository donViThucTapRepository;
    @Autowired
    public DangKyService(DangKyRepository dangKyRepository) {
        this.dangKyRepository = dangKyRepository;
    }

    public List<DangKy> getAllDangKy() {
        return dangKyRepository.findAll();
    }
    public List<DangKy> getAllDangKyByMaDvtt(Integer maDvtt) {
        return dangKyRepository.findByBaiDangDonViThucTapMaDvtt(maDvtt);
    }
    public List<DangKy> getBaiDangDaDangKyCuaSinhVien(Integer sinhVienId) {
        return dangKyRepository.findBySinhVien( sinhVienRepository.findById(sinhVienId).orElse(null));
    }
    public DangKy getDangKyById(Integer id) {
        return dangKyRepository.findById(id).orElse(null);
    }

    public DangKy createDangKy(DangKyDto dangKyDto, Integer baiDangId, Integer sinhVienId) {
        BaiDang baiDang = baiDangRepository.findById(baiDangId).orElse(null);
        if(baiDang.getSoLuong() == 0){
            return null;
        }
        else {
            SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).orElse(null);
            if (baiDang == null || sinhVien == null) {
                return null;
            }

            long existingDangKyCount = dangKyRepository.countBySinhVienAndBaiDang(sinhVien, baiDang);
            if (existingDangKyCount > 0) {
                return null;
            }

            dangKyDto.setTrangThai(0);
//        dangKyDto.setSv("fdgj.pdf");
            DangKy dangKy = new DangKy(
                    dangKyDto.getBangDiem(),
                    dangKyDto.getCv(),
                    dangKyDto.getTrangThai(),
                    baiDang,
                    sinhVien
            );

            return dangKyRepository.save(dangKy);
        }
    }


    public DangKy updateDangKy(Integer dangKyId, DangKyDto dangKyDto, Integer baiDangId, Integer sinhVienId) {
        DangKy existingDangKy = getDangKyById(dangKyId);
        if (existingDangKy != null) {
            existingDangKy.setBangDiem(dangKyDto.getBangDiem());
            existingDangKy.setCv(dangKyDto.getCv());

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
    public DangKy updateTrangThaiDangKy(Integer maDK,Integer baiDangId, Integer trangThai) {
        DangKy dangKyOptional = getDangKyById(maDK);
        if (dangKyOptional!= null) {
            if(trangThai == 1){
                BaiDang baiDang = baiDangRepository.findById(baiDangId).orElse(null);
                if(baiDang.getSoLuong() == 0){
                    return null;
                }
                else {
                    baiDang.setSoLuong(baiDang.getSoLuong()-1);
                    dangKyOptional.setTrangThai(trangThai);
                    return dangKyRepository.save(dangKyOptional);
                }
            }
            else {
                dangKyOptional.setTrangThai(trangThai);
                return dangKyRepository.save(dangKyOptional);
            }
        } else {
            return null;
        }
    }
    public boolean isBaiDangRegistered(Integer sinhVienId, Integer baiDangId) {
        // Thực hiện kiểm tra xem sinh viên có đăng ký bài đăng hay không
        // Sử dụng repository hoặc logic tương ứng
        // Trả về true nếu đã đăng ký, false nếu chưa đăng ký
        return dangKyRepository.existsBySinhVienAndBaiDang(
                sinhVienRepository.findById(sinhVienId).orElse(null),
                baiDangRepository.findById(baiDangId).orElse(null)
        );
    }

    public void deleteDangKy(Integer dangKyId) {
        DangKy existingDangKy = getDangKyById(dangKyId);
        if (existingDangKy != null) {
            dangKyRepository.delete(existingDangKy);
        }
    }
}

