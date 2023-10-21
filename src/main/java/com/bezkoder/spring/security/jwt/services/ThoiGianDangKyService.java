package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.Khoa;

import com.bezkoder.spring.security.jwt.entity.ThoiGianDangKy;

import com.bezkoder.spring.security.jwt.payload.request.ThoiGianDangKyDto;
import com.bezkoder.spring.security.jwt.repository.KhoaRepository;
import com.bezkoder.spring.security.jwt.repository.ThoiGianDangKyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ThoiGianDangKyService {
    @Autowired
    private ThoiGianDangKyRepository thoiGianDangKyRepository;
    @Autowired
    private KhoaRepository khoaRepository;
    public List<ThoiGianDangKy> getAllThoiGianDangKy() {
        return thoiGianDangKyRepository.findAll();
    }

    public ThoiGianDangKy getThoiGianDangKyById(Integer id) {
        return thoiGianDangKyRepository.findById(id).orElse(null);
    }
    public List<ThoiGianDangKy> getThoiGianDangKyByKhoa(Khoa khoa) {
        return thoiGianDangKyRepository.findByKhoa(khoa);
    }

    @Transactional
    public ThoiGianDangKy createThoiGianDangky(ThoiGianDangKyDto thoiGianDangKyDto, Integer khoa) {
        Optional<Khoa> khoaOptional = khoaRepository.findById(khoa);

        if (khoaOptional.isPresent()) {
            Khoa khoaid = khoaOptional.get();
            ThoiGianDangKy thoiGianDangKy = new ThoiGianDangKy(

                    thoiGianDangKyDto.getTgbd(),
                    thoiGianDangKyDto.getTgkt(),
                    thoiGianDangKyDto.getGhichu(),
                    khoaid
            );

            return thoiGianDangKyRepository.save(thoiGianDangKy);

        } else {
            return null;
        }
    }
    @Transactional
    public ThoiGianDangKy updateThoiGianDangKy(Integer id, ThoiGianDangKyDto thoiGianDangKyDto) {
        ThoiGianDangKy existingThoiGianDangKy = getThoiGianDangKyById(id);
        if (existingThoiGianDangKy != null) {
            existingThoiGianDangKy.setTgbd(thoiGianDangKyDto.getTgbd());
            existingThoiGianDangKy.setTgkt(thoiGianDangKyDto.getTgkt());
            existingThoiGianDangKy.setGhichu(thoiGianDangKyDto.getGhichu());
            return  thoiGianDangKyRepository.save(existingThoiGianDangKy);
        }
        return null;
    }

    @Transactional
    public void deleteThoiGianDangKy(Integer id) {
        ThoiGianDangKy existingThoiGianDangKy = getThoiGianDangKyById(id);
        if (existingThoiGianDangKy != null) {
            thoiGianDangKyRepository.delete(existingThoiGianDangKy);
        }
    }
}
