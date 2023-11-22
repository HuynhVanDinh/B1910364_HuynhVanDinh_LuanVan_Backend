package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.Khoa;

import com.bezkoder.spring.security.jwt.entity.ThoiGianDangKy;

import com.bezkoder.spring.security.jwt.payload.request.ThoiGianDangKyDto;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.repository.KhoaRepository;
import com.bezkoder.spring.security.jwt.repository.ThoiGianDangKyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ThoiGianDangKyService {
    @Autowired
    private ThoiGianDangKyRepository thoiGianDangKyRepository;
    @Autowired
    private KhoaRepository khoaRepository;

    private static final Logger logger = LoggerFactory.getLogger(ThoiGianDangKyService.class);
    public List<ThoiGianDangKy> getAllThoiGianDangKy() {
        return thoiGianDangKyRepository.findAll();
    }

    public ThoiGianDangKy getThoiGianDangKyById(Integer id) {
        return thoiGianDangKyRepository.findById(id).orElse(null);
    }
    public ThoiGianDangKy getThoiGianDangKyByKhoa(Khoa khoa) {
        return thoiGianDangKyRepository.findByKhoa(khoa);
    }

//    @Transactional
//    public ThoiGianDangKy createThoiGianDangky(ThoiGianDangKyDto thoiGianDangKyDto, Integer khoa) {
//        Optional<Khoa> khoaOptional = khoaRepository.findById(khoa);
//
//        if (khoaOptional.isPresent()) {
//            Khoa khoaid = khoaOptional.get();
//            ThoiGianDangKy thoiGianDangKy = new ThoiGianDangKy(
//
//                    thoiGianDangKyDto.getTgbd(),
//                    thoiGianDangKyDto.getTgkt(),
//                    thoiGianDangKyDto.getGhichu(),
//                    khoaid
//            );
//
//            return thoiGianDangKyRepository.save(thoiGianDangKy);
//
//        } else {
//            return null;
//        }
//    }
@Transactional
public ThoiGianDangKy createThoiGianDangky(ThoiGianDangKyDto thoiGianDangKyDto, Integer khoaId) {
    Optional<Khoa> khoaOptional = khoaRepository.findById(khoaId);

    if (khoaOptional.isPresent()) {
        Khoa khoa = khoaOptional.get();

        // Kiểm tra xem đã có thời gian đăng ký cho khoa này chưa
        boolean hasExistingThoiGian = thoiGianDangKyRepository.existsByKhoa(khoa);
        if (hasExistingThoiGian) {
            logger.warn("Đã có thời gian đăng ký cho khoa {}", khoaId);
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Đã có thời gian đăng ký cho khoa này");
        }

        // Kiểm tra tính hợp lệ của thời gian đăng ký
        LocalDate tgbd = thoiGianDangKyDto.getTgbd();
        LocalDate tgkt = thoiGianDangKyDto.getTgkt();
        if (tgbd == null || tgkt == null || !tgkt.isAfter(tgbd))   {
            logger.warn("Thời gian đăng ký không hợp lệ");
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Thời gian đăng ký không hợp lệ");
        }

        // Kiểm tra xem có thời gian đăng ký khác đang trùng lặp không
        boolean hasOverlap = thoiGianDangKyRepository.existsByTgbdBeforeAndTgktAfter(tgkt, tgbd);
        if (hasOverlap) {
            logger.warn("Thời gian đăng ký trùng lặp với khoa khác");
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Thời gian đăng ký trùng lặp với khoa khác");
        }

        ThoiGianDangKy thoiGianDangKy = new ThoiGianDangKy(
                tgbd,
                tgkt,
                thoiGianDangKyDto.getGhichu(),
                khoa
        );

        return thoiGianDangKyRepository.save(thoiGianDangKy);
    } else {
        logger.warn("Không tìm thấy khoa {}", khoaId);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khoa");
    }
}

    @Transactional
    public ThoiGianDangKy updateThoiGianDangKy(Integer id, ThoiGianDangKyDto thoiGianDangKyDto) {
        ThoiGianDangKy existingThoiGianDangKy = getThoiGianDangKyById(id);
        if (existingThoiGianDangKy != null) {
            // Kiểm tra tính hợp lệ của thời gian đăng ký
            LocalDate tgbd = thoiGianDangKyDto.getTgbd();
            LocalDate tgkt = thoiGianDangKyDto.getTgkt();
            if (tgbd == null || tgkt == null || !tgkt.isAfter(tgbd))   {
                logger.warn("Thời gian đăng ký không hợp lệ");
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Thời gian đăng ký không hợp lệ");
            }

            // Kiểm tra xem có thời gian đăng ký khác đang trùng lặp không
            boolean hasOverlap = thoiGianDangKyRepository.existsByTgbdBeforeAndTgktAfterAndIdNot(tgkt, tgbd, existingThoiGianDangKy.getId_tgdk());
            if (hasOverlap) {
                logger.warn("Thời gian đăng ký trùng lặp với khoa khác");
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Thời gian đăng ký trùng lặp với khoa khác");
            }

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
