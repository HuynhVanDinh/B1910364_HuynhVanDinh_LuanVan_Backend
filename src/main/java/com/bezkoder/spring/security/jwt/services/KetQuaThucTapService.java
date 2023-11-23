package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;

import com.bezkoder.spring.security.jwt.payload.request.KetQuaThucTapDto;
import com.bezkoder.spring.security.jwt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KetQuaThucTapService {
    @Autowired
    private KetQuaThucTapRepository ketQuaThucTapRepository;
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private DotThucTapRepository dotThucTapRepository;
    @Autowired
    private GiangVienRepository giangVienRepository;
    @Autowired
    private DonViThucTapRepository donViThucTapRepository;
    @Autowired
    private CanBoRepository canBoRepository;
    @Autowired
    private  DangKyRepository dangKyRepository;
    @Autowired
    private TuanRepository tuanRepository;
    @Autowired
    private EmailService emailService;
    public List<KetQuaThucTap> getAllKetQuaThucTap() {
        return ketQuaThucTapRepository.findAll();
    }
    public KetQuaThucTap getKetQuaThucTapById(Integer id) {
        return ketQuaThucTapRepository.findById(id).orElse(null);
    }
    public List<KetQuaThucTap> getKetQuaThucTapByMaDvtt(Integer madvtt){
       DonViThucTap donViThucTap = donViThucTapRepository.findById(madvtt).orElse(null);
        return ketQuaThucTapRepository.findByDonViThucTap(donViThucTap);
    }
    public List<KetQuaThucTap> getKetQuaThucTapByDot(Integer dot){
        DotThucTap dotThucTap = dotThucTapRepository.findById(dot).orElse(null);
//        DonViThucTap donViThucTap = donViThucTapRepository.findById(madvtt).orElse(null);
        return ketQuaThucTapRepository.findByDotThucTap(dotThucTap);
    }
    public List<KetQuaThucTap> getKetQuaThucTapChuaPhanCong(Integer madvtt) {
        DonViThucTap donViThucTap = donViThucTapRepository.findById(madvtt).orElse(null);

        if (donViThucTap == null) {
            return  null;
        }

        return ketQuaThucTapRepository.findByDonViThucTapAndCanBoHuongDanIsNull(donViThucTap);
    }

    public List<KetQuaThucTap> getKetQuaThucTapByMaGv(Integer magv, Integer trangThai){
        return ketQuaThucTapRepository.findByGiangVienAndTrangThai(giangVienRepository.findById(magv).orElse(null), trangThai);
    }
    public List<KetQuaThucTap> getKetQuaThucTapByMaCb(Integer macb, Integer trangThai){
        CanBo canBohuongdan = canBoRepository.findById(macb).orElse(null);
        return ketQuaThucTapRepository.findByCanBoHuongDanAndTrangThai(canBohuongdan,trangThai);
    }

    public List<KetQuaThucTap> getAllKetQuaThucTapByMaCb(Integer macb){
        CanBo canBohuongdan = canBoRepository.findById(macb).orElse(null);
        return ketQuaThucTapRepository.findByCanBoHuongDan(canBohuongdan);
    }

    public List<KetQuaThucTap> searchKetQuaThucTap(String keyword) {
        return ketQuaThucTapRepository.searchKetQuaThucTap(keyword);
    }

    public List<KetQuaThucTap> searchKetQuaThucTapByGiangVien(String keyword, int magv) {
        return ketQuaThucTapRepository.searchKetQuaThucTapByGiangVien(keyword, magv);
    }


    public KetQuaThucTap getKetQuaTHucTapByMaSv(Integer masv){
        SinhVien sinhVien = sinhVienRepository.findById(masv).orElse(null);
        return ketQuaThucTapRepository.findBySinhVien(sinhVien).orElse(null);
    }

    @Transactional
    public KetQuaThucTap createKetQuaThucTap( Integer maSV, Integer maDvtt, Integer maGv, Integer maDot) {
        Optional<SinhVien> sinhVienOptional = sinhVienRepository.findById(maSV);
        SinhVien sinhVien1 = sinhVienRepository.findById(maSV).orElse(null);
        Optional<DonViThucTap> donViThucTapOptional = donViThucTapRepository.findById(maDvtt);
        Optional<GiangVien> giangVienOptional = giangVienRepository.findById(maGv);
        Optional<DotThucTap> dotThucTapOptional = dotThucTapRepository.findById(maDot);
        Optional<DangKy> dangKyOptional = dangKyRepository.findByBaiDangDonViThucTapMaDvttAndSinhVien(maDvtt,sinhVien1);
        if ( dangKyOptional.isPresent() || sinhVienOptional.isPresent() || donViThucTapOptional.isPresent() || giangVienOptional.isPresent() || dotThucTapOptional.isPresent()) {
            DangKy dangKy = dangKyOptional.get();
            SinhVien sinhVien = sinhVienOptional.get();
            DonViThucTap donViThucTap = donViThucTapOptional.get();
            DotThucTap dotThucTap = dotThucTapOptional.get();
            GiangVien giangVien = giangVienOptional.get();
            dangKy.setTrangThai(3);
            KetQuaThucTap ketQuaThucTap = new KetQuaThucTap(
                    sinhVien,
                    donViThucTap,
                    giangVien,
                    dotThucTap
            );
            ketQuaThucTap.setCanBoHuongDan(null);
            ketQuaThucTap.setTrangThai(0);
            ketQuaThucTap.setDiem(null);
            KetQuaThucTap saveKetQuaThucTap = ketQuaThucTapRepository.save(ketQuaThucTap);
            return  saveKetQuaThucTap;
        } else {
            return null;
        }
    }

    @Transactional
    public KetQuaThucTap KhoaPhanKetQuaThucTap( Integer maSV, Integer maDvtt, Integer maGv, Integer maDot) {
        Optional<SinhVien> sinhVienOptional = sinhVienRepository.findById(maSV);
        SinhVien sinhVien1 = sinhVienRepository.findById(maSV).orElse(null);
        Optional<DonViThucTap> donViThucTapOptional = donViThucTapRepository.findById(maDvtt);
        Optional<GiangVien> giangVienOptional = giangVienRepository.findById(maGv);
        Optional<DotThucTap> dotThucTapOptional = dotThucTapRepository.findById(maDot);

        if ( sinhVienOptional.isPresent() || donViThucTapOptional.isPresent() || giangVienOptional.isPresent() || dotThucTapOptional.isPresent()) {

            SinhVien sinhVien = sinhVienOptional.get();
            DonViThucTap donViThucTap = donViThucTapOptional.get();
            DotThucTap dotThucTap = dotThucTapOptional.get();
            GiangVien giangVien = giangVienOptional.get();

            KetQuaThucTap ketQuaThucTap = new KetQuaThucTap(
                    sinhVien,
                    donViThucTap,
                    giangVien,
                    dotThucTap
            );
            String studentEmail = sinhVien.getAccount().getEmail();
            String subject = "Về việc Thực tập thực tế (TTTT).";
            String content = "Chào em, "+sinhVien.getTenSV()+",\n"+"Hiện tại em được phân công cho "+donViThucTap.getTenDvtt()+"\n Trân trọng, Quản trị viên";
            emailService.sendEmail(studentEmail, subject, content);

            ketQuaThucTap.setCanBoHuongDan(null);
            ketQuaThucTap.setTrangThai(0);
            ketQuaThucTap.setDiem(null);
            KetQuaThucTap saveKetQuaThucTap = ketQuaThucTapRepository.save(ketQuaThucTap);
            return  saveKetQuaThucTap;
        } else {
            return null;
        }
    }
    @Transactional
    public KetQuaThucTap updateKetQuaThucTap(KetQuaThucTapDto ketQuaThucTapDto, Integer maDot) {
        KetQuaThucTap existingKetQuaThucTap = getKetQuaThucTapById(ketQuaThucTapDto.getMaKqtt());
        DotThucTap dotThucTap = dotThucTapRepository.findById(maDot).orElse(null);
        if (existingKetQuaThucTap == null || dotThucTap == null) {
            return null;
        }
        existingKetQuaThucTap.setDotThucTap(dotThucTap);
        return ketQuaThucTapRepository.save(existingKetQuaThucTap);
    }
    @Transactional
    public KetQuaThucTap updateCanBo(Integer maSV, Integer maCB) {
        KetQuaThucTap existingKetQuaThucTap = getKetQuaTHucTapByMaSv(maSV);
        CanBo canBo = canBoRepository.findById(maCB).orElse(null);
        if (existingKetQuaThucTap == null || canBo == null) {
            return null;
        }
        existingKetQuaThucTap.setCanBoHuongDan(canBo);
        return ketQuaThucTapRepository.save(existingKetQuaThucTap);
    }
    @Transactional
    public KetQuaThucTap updateDiemKetQuaThucTap(KetQuaThucTapDto ketQuaThucTapDto, Integer maGv) {
        KetQuaThucTap existingKetQuaThucTap = getKetQuaThucTapById(ketQuaThucTapDto.getMaKqtt());
        GiangVien giangVien = giangVienRepository.findById(maGv).orElse(null);

        if (existingKetQuaThucTap == null || giangVien == null) {
            return null;
        }
        existingKetQuaThucTap.setDiem(ketQuaThucTapDto.getDiem());
        existingKetQuaThucTap.setTrangThai(3);
        CanBo canBo = canBoRepository.findById(ketQuaThucTapDto.getCanbo()).orElse(null);
        List<Tuan>  existingTuan = tuanRepository.findByCanboAndIsComplete(canBo,1);
        for (Tuan tuan : existingTuan) {
            tuan.setIsComplete(2);
        }
        tuanRepository.saveAll(existingTuan);
        return ketQuaThucTapRepository.save(existingKetQuaThucTap);
    }
    @Transactional
    public KetQuaThucTap updateTrangThai(KetQuaThucTapDto ketQuaThucTapDto) {
        KetQuaThucTap existingKetQuaThucTap = getKetQuaThucTapById(ketQuaThucTapDto.getMaKqtt());

        if (existingKetQuaThucTap == null ) {
            return null;
        }
        existingKetQuaThucTap.setTrangThai(ketQuaThucTapDto.getTrangThai());
        CanBo canBo = canBoRepository.findById(ketQuaThucTapDto.getCanbo()).orElse(null);
        List<Tuan>  existingTuan = tuanRepository.findByCanboAndIsComplete(canBo,0);
        for (Tuan tuan : existingTuan) {
            tuan.setIsComplete(1);
        }
        tuanRepository.saveAll(existingTuan);
        return ketQuaThucTapRepository.save(existingKetQuaThucTap);
    }
    @Transactional
    public List<KetQuaThucTap> updateMultipleKetQuaThucTapWithSingleCanBo(List<KetQuaThucTap> ketQuaThucTaps, Integer maCb) {
        List<KetQuaThucTap> updatedKetQuaThucTaps = new ArrayList<>();
        CanBo canBo = canBoRepository.findById(maCb).orElse(null);
        if (canBo == null) {
            // Xử lý khi không tìm thấy cán bộ
            return null;
        }

        for (KetQuaThucTap ketQuaThucTap : ketQuaThucTaps) {
            KetQuaThucTap existingKetQuaThucTap = getKetQuaThucTapById(ketQuaThucTap.getMaKqtt());
            if (existingKetQuaThucTap != null) {
                existingKetQuaThucTap.setCanBoHuongDan(canBo);
                updatedKetQuaThucTaps.add(ketQuaThucTapRepository.save(existingKetQuaThucTap));
            }
        }

        return updatedKetQuaThucTaps;
    }


    @Transactional
    public void deleteKetQuaThucTap(Integer makqtt) {
        KetQuaThucTap existingKetQuaThucTap = getKetQuaThucTapById(makqtt);

        if (existingKetQuaThucTap != null) {
            ketQuaThucTapRepository.delete(existingKetQuaThucTap);
        }
    }
}
