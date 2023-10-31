package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.GiangVien;
import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.Lop;
import com.bezkoder.spring.security.jwt.payload.request.LopDto;
import com.bezkoder.spring.security.jwt.repository.GiangVienRepository;
import com.bezkoder.spring.security.jwt.repository.KhoaRepository;
import com.bezkoder.spring.security.jwt.repository.LopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LopService {
    @Autowired
    private LopRepository lopRepository;
    @Autowired
    private GiangVienRepository giangVienRepository;
    public List<Lop> getAllLop() {
        return lopRepository.findAll();
    }

    public Lop getLopById(Integer id) {
        return lopRepository.findById(id).orElse(null);
    }

    @Autowired
    private KhoaRepository khoaRepository;

    @Transactional
    public Lop createLop(LopDto lopDto, Integer khoaId) {
        Optional<Khoa> khoaOptional = khoaRepository.findById(khoaId);

        if (khoaOptional.isPresent()) {
            Khoa khoa = khoaOptional.get();
            Lop lop = new Lop(lopDto.getTenLop(), khoa);
            lopRepository.save(lop);
            return lop;
        } else {
            return null;
        }
    }

    @Transactional
    public Lop updateLop(Integer lopId, LopDto lopDto, Integer khoaId) {
        Optional<Lop> existingLopOptional = lopRepository.findById(lopId);
        Optional<Khoa> khoaOptional = khoaRepository.findById(khoaId);

        if (existingLopOptional.isPresent() && khoaOptional.isPresent()) {
            Lop existingLop = existingLopOptional.get();
            Khoa khoa = khoaOptional.get();

            existingLop.setTenLop(lopDto.getTenLop());
            existingLop.setKhoa(khoa);

            return lopRepository.save(existingLop);
        } else {
            // Xử lý trường hợp không tìm thấy lớp hoặc khoa
            // Ví dụ: return null hoặc throw exception tùy theo yêu cầu
            return null;
        }
    }
    @Transactional
    public Lop updateLopGiangVien(Integer lopId, Integer maGV) {
        Optional<Lop> existingLopOptional = lopRepository.findById(lopId);
        Optional<GiangVien> giangVienOptional = giangVienRepository.findById(maGV);
        if (existingLopOptional.isPresent() &&  giangVienOptional.isPresent()) {
            Lop existingLop = existingLopOptional.get();
            GiangVien giangVien =  giangVienOptional.get();
            existingLop.setGiangVien(giangVien);
            return lopRepository.save(existingLop);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteLop(Integer id) {
        lopRepository.deleteById(id);
    }

    @Transactional
    public List<Lop> searchLopByName(String tenLop) {
        return lopRepository.findByTenLopContainingIgnoreCase(tenLop);
    }
}
