package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.MucDanhGiaCuaCanBo;
import com.bezkoder.spring.security.jwt.entity.MucDanhGiaCuaGiangVien;
import com.bezkoder.spring.security.jwt.entity.PhieuDiemCanbo;
import com.bezkoder.spring.security.jwt.payload.request.MucDanhGiaCuaCanBoDto;
import com.bezkoder.spring.security.jwt.repository.KhoaRepository;
import com.bezkoder.spring.security.jwt.repository.MucDanhGiaCuaCanBoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MucDanhGiaCuaCanBoService {
    private final MucDanhGiaCuaCanBoRepository mucDanhGiaCuaCanBoRepository;

    @Autowired
    private KhoaRepository khoaRepository;
    @Autowired
    public MucDanhGiaCuaCanBoService(MucDanhGiaCuaCanBoRepository mucDanhGiaCuaCanBoRepository) {
        this.mucDanhGiaCuaCanBoRepository = mucDanhGiaCuaCanBoRepository;
    }
    public List<MucDanhGiaCuaCanBo> getAllMucDanhGiaCuaCanBo() {
        return mucDanhGiaCuaCanBoRepository.findAll();
    }
    public MucDanhGiaCuaCanBo getMucDanhGiaCuaCanBoById(Integer id) {
        return mucDanhGiaCuaCanBoRepository.findById(id).orElse(null);
    }

    public MucDanhGiaCuaCanBo createMucDanhGiaCuaCanBo(MucDanhGiaCuaCanBoDto mucDanhGiaCuaCanBoDto) {
        MucDanhGiaCuaCanBo mucDanhGiaCuaCanBo = new MucDanhGiaCuaCanBo(
                mucDanhGiaCuaCanBoDto.getTenMuc()
        );
        return mucDanhGiaCuaCanBoRepository.save(mucDanhGiaCuaCanBo);

    }

    public MucDanhGiaCuaCanBo updateMucDanhGiaCuaCanBo(Integer mucDanhGiaCuaCanBoId, MucDanhGiaCuaCanBoDto mucDanhGiaCuaCanBoDto) {
        MucDanhGiaCuaCanBo existingMucDanhGiaCuaCanBo = getMucDanhGiaCuaCanBoById(mucDanhGiaCuaCanBoId);
        if (existingMucDanhGiaCuaCanBo != null) {
            existingMucDanhGiaCuaCanBo.setTenMuc(mucDanhGiaCuaCanBoDto.getTenMuc());
            return mucDanhGiaCuaCanBoRepository.save(existingMucDanhGiaCuaCanBo);
        }
        return null;
    }

    public void deleteMucDanhGiaCuaCanBo(Integer mucDanhGiaCuaCanBoId) {
        MucDanhGiaCuaCanBo existingMucDanhGiaCuaCanBo = getMucDanhGiaCuaCanBoById(mucDanhGiaCuaCanBoId);
        if (existingMucDanhGiaCuaCanBo != null) {
            mucDanhGiaCuaCanBoRepository.delete(existingMucDanhGiaCuaCanBo);
        }
    }
}
