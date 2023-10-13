package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.BaiDang;
import com.bezkoder.spring.security.jwt.entity.DonViThucTap;
import com.bezkoder.spring.security.jwt.payload.request.BaiDangDto;
import com.bezkoder.spring.security.jwt.repository.BaiDangRepository;
import com.bezkoder.spring.security.jwt.repository.DonViThucTapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BaiDangService {

    private final BaiDangRepository baiDangRepository;

    @Autowired
    public BaiDangService(BaiDangRepository baiDangRepository) {
        this.baiDangRepository = baiDangRepository;
    }

    @Autowired
    private DonViThucTapRepository donViThucTapRepository;

    public List<BaiDang> getAllBaiDang() {
        return baiDangRepository.findAll();
    }

    public BaiDang getBaiDangById(Integer id) {
        return baiDangRepository.findById(id).orElse(null);
    }

    public BaiDang createBaiDang(BaiDangDto baiDangDto, Integer donViThucTapId) {
        DonViThucTap donViThucTap = donViThucTapRepository.findByAccountId(donViThucTapId).orElse(null);

        if (donViThucTap == null) {
            return null;
        }
        BaiDang baiDang = new BaiDang();
        baiDang.setNoiDung(baiDangDto.getNoiDung());
        baiDang.setSoLuong(baiDangDto.getSoLuong());
        baiDang.setTroCap(baiDangDto.getTroCap());
        baiDang.setNgayDang(LocalDate.now());
        baiDang.setNgaySua(LocalDate.now());
        baiDang.setDonViThucTap(donViThucTap);

        return baiDangRepository.save(baiDang);

    }

    public BaiDang updateBaiDang(Integer baiDangId, BaiDangDto baiDangDto, Integer donViThucTapId) {
        BaiDang existingBaiDang = getBaiDangById(baiDangId);
        DonViThucTap donViThucTap = donViThucTapRepository.findByAccountId(donViThucTapId).orElse(null);

        if (existingBaiDang == null || donViThucTap == null) {
            return null;
        }

            existingBaiDang.setNoiDung(baiDangDto.getNoiDung());
            existingBaiDang.setSoLuong(baiDangDto.getSoLuong());
            existingBaiDang.setTroCap(baiDangDto.getTroCap());
            existingBaiDang.setNgaySua(LocalDate.now());
            existingBaiDang.setDonViThucTap(donViThucTap);

            return baiDangRepository.save(existingBaiDang);

    }

    public void deleteBaiDang(Integer baiDangId) {
        BaiDang existingBaiDang = getBaiDangById(baiDangId);
        if (existingBaiDang != null) {
            baiDangRepository.delete(existingBaiDang);
        }
    }
}
