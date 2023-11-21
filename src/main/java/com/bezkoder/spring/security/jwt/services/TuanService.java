package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.CanBo;
import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.ThoiGianDangKy;
import com.bezkoder.spring.security.jwt.entity.Tuan;
import com.bezkoder.spring.security.jwt.payload.request.TuanDto;
import com.bezkoder.spring.security.jwt.repository.CanBoRepository;
import com.bezkoder.spring.security.jwt.repository.TuanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Tuan createTuan(TuanDto tuanDto, Integer macb) {
        Optional<CanBo> canboOptional = canBoRepository.findById(macb);

        if (canboOptional.isPresent()) {
            CanBo maCB = canboOptional.get();
            int soTuansDaTonTai = tuanRepository.countTuansByMaCBAndIsComplete(maCB.getMaCB(),0);
            String tenTuan = "Tuần " + (soTuansDaTonTai + 1);
            Tuan tuan = new Tuan(
                    tenTuan,
                    tuanDto.getBatdau(),
                    tuanDto.getHethan(),
                    tuanDto.getSo_buoi(),
                    maCB
            );
            tuan.setIsComplete(0);
            return  tuanRepository.save(tuan);
        } else {
            return null;
        }
    }

    @Transactional
    public Tuan updateTuan(Integer id, TuanDto tuanDto) {
        Tuan existingTuan = getTuanById(id);
        if (existingTuan != null) {
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
}
