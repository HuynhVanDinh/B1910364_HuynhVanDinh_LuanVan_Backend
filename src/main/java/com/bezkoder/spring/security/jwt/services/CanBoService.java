package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.CanBoDto;
import com.bezkoder.spring.security.jwt.repository.CanBoRepository;
import com.bezkoder.spring.security.jwt.repository.DonViThucTapRepository;
import com.bezkoder.spring.security.jwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class CanBoService {
    @Autowired
    private CanBoRepository canBoRepository;

    @Autowired
    private DonViThucTapRepository donViThucTapRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<CanBo> getAllCanBo() {
        return canBoRepository.findAll();
    }

    public CanBo getCanBoById(Integer id) {
        return canBoRepository.findById(id).orElse(null);
    }
    @Transactional
    public CanBo createCanBo(CanBoDto canBoDto, Integer donViThucTapId, String username, String password, String email) {
        DonViThucTap donViThucTap = donViThucTapRepository.findById(donViThucTapId).orElse(null);

        if (donViThucTap == null) {
            return null;
        }

        CanBo canBo = new CanBo(
                canBoDto.getTenCB(),
                canBoDto.getGioiTinh(),
                canBoDto.getNgSinh(),
                canBoDto.getSdtCB(),
                donViThucTap
        );
//        canBo.setGTinh("nam");
        CanBo savedCanBo = canBoRepository.save(canBo);

        // Tạo tài khoản cho cán bộ
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        Account taiKhoan = new Account();
        taiKhoan.setUsername(username);
        taiKhoan.setPassword(encodedPassword);
        taiKhoan.setEmail(email);

        Role unitRole = roleRepository.findByName(ERole.ROLE_CADRE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        taiKhoan.setRoles(Set.of(unitRole));

        canBo.setAccount(taiKhoan);
        canBoRepository.save(canBo);

        return savedCanBo;
    }

    @Transactional
    public CanBo updateCanBo(Integer canBoId, CanBoDto canBoDto, Integer donViThucTapId) {
        CanBo existingCanBo = getCanBoById(canBoId);
        DonViThucTap donViThucTap = donViThucTapRepository.findById(donViThucTapId).orElse(null);

        if (existingCanBo == null || donViThucTap == null) {
            return null;
        }

        existingCanBo.setTenCB(canBoDto.getTenCB());
        existingCanBo.setGTinh(canBoDto.getGioiTinh());
        existingCanBo.setNgSinh(canBoDto.getNgSinh());
        existingCanBo.setSdtCB(canBoDto.getSdtCB());
        existingCanBo.setDonViThucTap(donViThucTap);

        return canBoRepository.save(existingCanBo);
    }

    @Transactional
    public void deleteCanBo(Integer canBoId) {
        CanBo existingCanBo = getCanBoById(canBoId);

        if (existingCanBo != null) {
            canBoRepository.delete(existingCanBo);
        }
    }
}
