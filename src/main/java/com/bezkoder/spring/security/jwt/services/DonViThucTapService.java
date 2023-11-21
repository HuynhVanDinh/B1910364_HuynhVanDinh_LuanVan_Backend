package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.DonViThucTapDto;
import com.bezkoder.spring.security.jwt.repository.AccountRepository;
import com.bezkoder.spring.security.jwt.repository.RoleRepository;
import com.bezkoder.spring.security.jwt.repository.DonViThucTapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class DonViThucTapService {
    @Autowired
    private DonViThucTapRepository donViThucTapRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;


    public List<DonViThucTap> getAllDonViThucTap() {
        return donViThucTapRepository.findAll();
    }
    public DonViThucTap getDonViThucTapByAccountId(Integer accountid) {
        return donViThucTapRepository.findByAccountId(accountid).orElse(null);
    }
    public DonViThucTap getDonViThucTapById(Integer id) {
        return donViThucTapRepository.findById(id).orElse(null);
    }

    public List<DonViThucTap> getDonViThucTapByIsKhoa(Integer trangthai) {
        return donViThucTapRepository.findDonViThucTapByIsKhoa(trangthai);
    }
    @Transactional
    public DonViThucTap createDonViThucTap(DonViThucTapDto donViThucTapDto, String email) {
        DonViThucTap donViThucTap = new DonViThucTap();
        donViThucTap.setTenDvtt(donViThucTapDto.getTenDvtt());
        donViThucTap.setDiaChi(donViThucTapDto.getDiaChi());
        donViThucTap.setSoDt(donViThucTapDto.getSoDt());
        donViThucTap.setIsKhoa(donViThucTapDto.getIsKhoa());

        DonViThucTap savedDonViThucTap = donViThucTapRepository.save(donViThucTap);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("123123");
        Account taiKhoan = new Account();
        taiKhoan.setUsername(savedDonViThucTap.getTenDvtt());
        taiKhoan.setPassword(encodedPassword);
        taiKhoan.setEmail(email);
        // Set default role as "ROLE_UNIT"
        Role unitRole = roleRepository.findByName(ERole.ROLE_UNIT)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        taiKhoan.setRoles(Set.of(unitRole));
        donViThucTap.setAccount(taiKhoan);
        donViThucTapRepository.save(donViThucTap);
//        accountRepository.save(taiKhoan);

        return savedDonViThucTap;
    }

    @Transactional
    public DonViThucTap updateDonViThucTap(Integer id, DonViThucTapDto donViThucTapDto, String email) {
        DonViThucTap existingDonViThucTap = getDonViThucTapById(id);
        if (existingDonViThucTap != null) {
            existingDonViThucTap.setTenDvtt(donViThucTapDto.getTenDvtt());
            existingDonViThucTap.setDiaChi(donViThucTapDto.getDiaChi());
            existingDonViThucTap.setSoDt(donViThucTapDto.getSoDt());
            existingDonViThucTap.setIsKhoa(donViThucTapDto.getIsKhoa());
            // Cập nhật thông tin của Account
            Account account = existingDonViThucTap.getAccount();
            account.setEmail(email);
            return donViThucTapRepository.save(existingDonViThucTap);
        }
        return null;
    }


    @Transactional
    public void deleteDonViThucTap(Integer id) {
        DonViThucTap existingDonViThucTap = getDonViThucTapById(id);
        if (existingDonViThucTap != null) {
            donViThucTapRepository.delete(existingDonViThucTap);
        }
    }

    @Transactional
    public List<DonViThucTap> searchDonViThucTapByName(String tenDvtt) {
        // Gọi phương thức tìm kiếm trong repository
        return donViThucTapRepository.findByTenDvttContainingIgnoreCase(tenDvtt);
    }
}
