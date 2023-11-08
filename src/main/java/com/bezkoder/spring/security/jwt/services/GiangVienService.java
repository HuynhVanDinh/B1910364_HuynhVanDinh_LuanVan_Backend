package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.GiangVienDto;
import com.bezkoder.spring.security.jwt.repository.GiangVienRepository;
import com.bezkoder.spring.security.jwt.repository.KhoaRepository;
import com.bezkoder.spring.security.jwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GiangVienService {
    @Autowired
    private GiangVienRepository giangVienRepository;

    @Autowired
    private KhoaRepository khoaRepository;

    @Autowired
    private RoleRepository roleRepository;
    public List<GiangVien> getAllGiangVien() {
        return giangVienRepository.findAll();
    }

    public GiangVien getGiangVienById(Integer id) {
        return giangVienRepository.findById(id).orElse(null);
    }

    public List<GiangVien> getGiangVienByKhoa(Integer khoaId) {
       Khoa khoa = khoaRepository.findById(khoaId).orElse(null);
        return giangVienRepository.findByKhoa(khoa);
    }
    public GiangVien getGiangVienByAccountId(Integer accountid) {
        return giangVienRepository.findByAccountId(accountid).orElse(null);
    }
    @Transactional
    public GiangVien createGiangVien(GiangVienDto giangVienDto, Integer khoaId, String username, String password, String email) {
        Optional<Khoa> khoaOptional = khoaRepository.findById(khoaId);
        if (khoaOptional.isPresent()) {
            Khoa khoa = khoaOptional.get();
            GiangVien giangVien = new GiangVien(
                    giangVienDto.getTenGV(),
                    giangVienDto.getAnhGV(),
                    khoa
            );
            GiangVien savedGiangVien = giangVienRepository.save(giangVien);
            // Tạo tài khoản cho giảng viên
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);
            Account taiKhoan = new Account();
            taiKhoan.setUsername(username);
            taiKhoan.setPassword(encodedPassword);
            taiKhoan.setEmail(email);
            Role unitRole = roleRepository.findByName(ERole.ROLE_LECTURER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            taiKhoan.setRoles(Set.of(unitRole));
            giangVien.setAccount(taiKhoan);
            giangVienRepository.save(giangVien);
            return savedGiangVien;
        } else {
            return null;
        }
    }
    @Transactional
    public GiangVien updateGiangVien(GiangVienDto giangVienDto, Integer khoaId) {
        GiangVien existingGiangVien = getGiangVienById(giangVienDto.getMaGV());
        Khoa khoa = khoaRepository.findById(khoaId).orElse(null);
        if (existingGiangVien == null || khoa == null) {
            return null;
        }
        existingGiangVien.setTenGV(giangVienDto.getTenGV());
        existingGiangVien.setKhoa(khoa);
        return giangVienRepository.save(existingGiangVien);
    }
    @Transactional
    public GiangVien updateAvt(Integer giangvienId, String hinhAnh) {
        GiangVien existingGiangVien = getGiangVienById(giangvienId);
        existingGiangVien.setAnhGV(hinhAnh);
        return giangVienRepository.save(existingGiangVien);
    }
    @Transactional
    public void deleteGiangVien(Integer giangVienId) {
        GiangVien existingGiangVien = getGiangVienById(giangVienId);

        if (existingGiangVien != null) {
            giangVienRepository.delete(existingGiangVien);
        }
    }
}
