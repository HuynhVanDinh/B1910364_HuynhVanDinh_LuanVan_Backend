package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.CanBo;

import com.bezkoder.spring.security.jwt.entity.Tuan;
import com.bezkoder.spring.security.jwt.payload.request.TuanDto;
import com.bezkoder.spring.security.jwt.repository.CanBoRepository;
import com.bezkoder.spring.security.jwt.repository.TuanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class TuanService {
    @Autowired
    private TuanRepository tuanRepository;
    @Autowired
    private CanBoRepository canBoRepository;

    public List<Tuan> getAllTuan() {
        return tuanRepository.findAll();
    }

    public Tuan getTuanById(Integer id) {
        return tuanRepository.findById(id).orElse(null);
    }
    public List<Tuan> getTuanByTrangThai(Integer trangThai) {
        return tuanRepository.findByIsComplete(trangThai);
    }
    public List<Tuan> getTuanByCanbo(Integer canbo) {
        CanBo macb =  canBoRepository.findById(canbo).orElse(null);
        return tuanRepository.findByCanboAndIsComplete(macb,0);
    }
    public List<Tuan> getTuanByCanboAndSinhvien(Integer canbo) {
        CanBo macb =  canBoRepository.findById(canbo).orElse(null);
        return tuanRepository.findByCanboAndIsComplete(macb,1);
    }
    @Transactional
    public Tuan createTuan(TuanDto tuanDto, Integer macb) {
        Optional<CanBo> canboOptional = canBoRepository.findById(macb);
        CanBo macb2 =  canBoRepository.findById(macb).orElse(null);
        if (canboOptional.isPresent()) {
            CanBo maCB = canboOptional.get();

            // Kiểm tra tính hợp lệ của thời gian (batdau không lớn hơn ketthuc)
            LocalDate batdau = tuanDto.getBatdau();
            LocalDate ketthuc = tuanDto.getHethan();
            if (batdau == null || ketthuc == null || !ketthuc.isAfter(batdau)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Thời gian không hợp lệ");
            }

            // Kiểm tra xem có tuần trùng lặp không
            boolean isOverlap = tuanRepository.existsByCanboAndBatdauBeforeAndHethanAfterAndIsComplete(macb2, ketthuc, batdau, 0);
            if (isOverlap) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Thời gian trùng lặp với tuần khác");
            }

            int soTuansDaTonTai = tuanRepository.countTuansByMaCBAndIsComplete(maCB.getMaCB(), 0);
            String tenTuan = "Tuần " + (soTuansDaTonTai + 1);
            Tuan tuan = new Tuan(
                    tenTuan,
                    batdau,
                    ketthuc,
                    tuanDto.getSo_buoi(),
                    maCB
            );
            tuan.setIsComplete(0);
            return tuanRepository.save(tuan);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy cán bộ");
        }
    }


    @Transactional
    public Tuan updateTuan(Integer id, TuanDto tuanDto) {
        Tuan existingTuan = getTuanById(id);
        if (existingTuan != null) {
            // Kiểm tra tính hợp lệ của thời gian (batdau không lớn hơn ketthuc)
            LocalDate batdau = tuanDto.getBatdau();
            LocalDate ketthuc = tuanDto.getHethan();
            if (batdau == null || ketthuc == null || !ketthuc.isAfter(batdau)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Thời gian không hợp lệ");
            }

            // Kiểm tra xem có tuần trùng lặp không
            boolean isOverlap = tuanRepository.existsByCanboAndBatdauBeforeAndHethanAfterAndIsComplete(existingTuan.getCanbo(), ketthuc, batdau, 0, id);

            if (isOverlap) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Thời gian trùng lặp với tuần khác");
            }
//            existingTuan.setTen_tuan(tuanDto.getTen_tuan());
            existingTuan.setBatdau(tuanDto.getBatdau());
            existingTuan.setHethan(tuanDto.getHethan());
            existingTuan.setSo_buoi(tuanDto.getSo_buoi());
            return  tuanRepository.save(existingTuan);
        }
        return null;
    }
    @Transactional
    public void deleteTuan(Integer id) {
        Tuan existingTuan= getTuanById(id);
        if (existingTuan != null) {
            tuanRepository.delete(existingTuan);
        }
    }

    @Transactional
    public void hideAllTuan() {
        List<Tuan> existingTuan = getAllTuan();
        if (existingTuan != null) {
            for (Tuan tuan : existingTuan) {
                tuan.setIsComplete(1);
            }
            tuanRepository.saveAll(existingTuan);
        }
    }

    @Transactional
    public void hideAllTuan2() {
        List<Tuan> existingTuan = getAllTuan();
        if (existingTuan != null) {
            for (Tuan tuan : existingTuan) {
                tuan.setIsComplete(2);
            }
            tuanRepository.saveAll(existingTuan);
        }
    }
}
