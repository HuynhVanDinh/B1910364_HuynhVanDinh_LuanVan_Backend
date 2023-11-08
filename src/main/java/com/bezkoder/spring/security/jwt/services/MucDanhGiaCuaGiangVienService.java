package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.MucDanhGiaCuaCanBo;
import com.bezkoder.spring.security.jwt.entity.MucDanhGiaCuaGiangVien;
import com.bezkoder.spring.security.jwt.payload.request.MucDanhGiaCuaCanBoDto;
import com.bezkoder.spring.security.jwt.payload.request.MucDanhGiaCuaGiangVienDto;
import com.bezkoder.spring.security.jwt.repository.KhoaRepository;
import com.bezkoder.spring.security.jwt.repository.MucDanhGiaCuaCanBoRepository;
import com.bezkoder.spring.security.jwt.repository.MucDanhGiaCuaGiangVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MucDanhGiaCuaGiangVienService {
    private final MucDanhGiaCuaGiangVienRepository mucDanhGiaCuaGiangVienRepository;

    @Autowired
    private KhoaRepository khoaRepository;
    @Autowired
    public MucDanhGiaCuaGiangVienService(MucDanhGiaCuaGiangVienRepository mucDanhGiaCuaGiangVienRepository) {
        this.mucDanhGiaCuaGiangVienRepository = mucDanhGiaCuaGiangVienRepository;
    }
    public List<MucDanhGiaCuaGiangVien> getAllMucDanhGiaCuaGiangVien() {
        return mucDanhGiaCuaGiangVienRepository.findAll();
    }
    public MucDanhGiaCuaGiangVien getMucDanhGiaCuaGiangVienById(Integer id) {
        return mucDanhGiaCuaGiangVienRepository.findById(id).orElse(null);
    }
    public List<MucDanhGiaCuaGiangVien> getMucDanhGiaCuaGiangVienByKhoa(Integer id) {
        Khoa khoa = khoaRepository.findById(id).orElse(null);
        return mucDanhGiaCuaGiangVienRepository.findMucDanhGiaCuaGiangVienByKhoa(khoa);
    }
    public MucDanhGiaCuaGiangVien createMucDanhGiaCuaGiangVien(Integer khoaId,MucDanhGiaCuaGiangVienDto mucDanhGiaCuaGiangVienDto) {
        Khoa khoa = khoaRepository.findById(khoaId).orElse(null);
        MucDanhGiaCuaGiangVien mucDanhGiaCuaGiangVien = new MucDanhGiaCuaGiangVien(
                mucDanhGiaCuaGiangVienDto.getTenMuc(),
                khoa
        );
        return mucDanhGiaCuaGiangVienRepository.save(mucDanhGiaCuaGiangVien);

    }

    public MucDanhGiaCuaGiangVien updateMucDanhGiaCuaGiangVien(Integer mucDanhGiaCuaGiangVienId, MucDanhGiaCuaGiangVienDto mucDanhGiaCuaGiangVienDto) {
        MucDanhGiaCuaGiangVien existingMucDanhGiaCuaGiangVien = getMucDanhGiaCuaGiangVienById(mucDanhGiaCuaGiangVienId);
        if (existingMucDanhGiaCuaGiangVien != null) {
            existingMucDanhGiaCuaGiangVien.setTenMuc(mucDanhGiaCuaGiangVienDto.getTenMuc());
            return mucDanhGiaCuaGiangVienRepository.save(existingMucDanhGiaCuaGiangVien);
        }
        return null;
    }

    public void deleteMucDanhGiaCuaGiangVien(Integer mucDanhGiaCuaGiangVienId) {
        MucDanhGiaCuaGiangVien existingMucDanhGiaCuaGiangVien = getMucDanhGiaCuaGiangVienById(mucDanhGiaCuaGiangVienId);
        if (existingMucDanhGiaCuaGiangVien != null) {
            mucDanhGiaCuaGiangVienRepository.delete(existingMucDanhGiaCuaGiangVien);
        }
    }
}
