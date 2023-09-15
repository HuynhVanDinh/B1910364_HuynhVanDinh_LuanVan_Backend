package com.bezkoder.spring.security.jwt.services;
import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import com.bezkoder.spring.security.jwt.payload.request.KhoaDto;
import com.bezkoder.spring.security.jwt.repository.KhoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KhoaService {
    @Autowired
    private KhoaRepository khoaRepository;

    public List<Khoa> getAllKhoa() {
        return khoaRepository.findAll();
    }

    public Khoa getKhoaById(Integer id) {
        return khoaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Khoa createKhoa(KhoaDto khoaDto) {
        Khoa khoa = new Khoa(khoaDto.getKhoaName(), khoaDto.getKhoaSdt(),khoaDto.getKhoaStatus());
        return khoaRepository.save(khoa);
    }

    @Transactional
    public Khoa updateKhoa(Integer id, KhoaDto khoaDto) {
        Optional<Khoa> khoaOptional = khoaRepository.findById(id);
        if (khoaOptional.isPresent()) {
            Khoa existingKhoa = khoaOptional.get();
            existingKhoa.setKhoaName(khoaDto.getKhoaName());
            existingKhoa.setKhoaSdt(khoaDto.getKhoaSdt());
            existingKhoa.setUpdatedAt(LocalDateTime.now());
            existingKhoa.setKhoaStatus(khoaDto.getKhoaStatus());
            return khoaRepository.save(existingKhoa);
        }
        return null;
    }


    @Transactional
    public void updateKhoaStatus(Khoa khoa) {
        khoa.setUpdatedAt(LocalDateTime.now());
        khoaRepository.save(khoa);
    }


    @Transactional
    public void deleteKhoa(Integer id) {
        khoaRepository.deleteById(id);
    }

    @Transactional
    public List<Khoa> searchKhoaByName(String khoaName) {
        // Gọi phương thức tìm kiếm trong repository
        return khoaRepository.findByKhoaNameContainingIgnoreCase(khoaName);
    }
}
