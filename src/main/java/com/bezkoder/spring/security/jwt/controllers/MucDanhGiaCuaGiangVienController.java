package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.MucDanhGiaCuaGiangVien;
import com.bezkoder.spring.security.jwt.payload.request.MucDanhGiaCuaGiangVienDto;
import com.bezkoder.spring.security.jwt.services.MucDanhGiaCuaGiangVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mucdanhgiacuagiangvien")
public class MucDanhGiaCuaGiangVienController {
    @Autowired
    private MucDanhGiaCuaGiangVienService mucDanhGiaCuaGiangVienService;

    @GetMapping
    public ResponseEntity<List<MucDanhGiaCuaGiangVien>> getAllMucDanhGiaCuaGiangVien() {
        List<MucDanhGiaCuaGiangVien> mucDanhGiaCuaGiangVienList = mucDanhGiaCuaGiangVienService.getAllMucDanhGiaCuaGiangVien();
        return new ResponseEntity<>(mucDanhGiaCuaGiangVienList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MucDanhGiaCuaGiangVien> getMucDanhGiaCuaGiangVienById(@PathVariable Integer id) {
        MucDanhGiaCuaGiangVien mucDanhGiaCuaGiangVien = mucDanhGiaCuaGiangVienService.getMucDanhGiaCuaGiangVienById(id);
        if (mucDanhGiaCuaGiangVien != null) {
            return new ResponseEntity<>(mucDanhGiaCuaGiangVien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/khoa/{khoaId}")
    public ResponseEntity<List<MucDanhGiaCuaGiangVien>> getMucDanhGiaCuaGiangVienByKhoa(@PathVariable Integer khoaId) {
        List<MucDanhGiaCuaGiangVien> mucDanhGiaCuaGiangVien = mucDanhGiaCuaGiangVienService.getMucDanhGiaCuaGiangVienByKhoa(khoaId);
        if (mucDanhGiaCuaGiangVien != null) {
            return new ResponseEntity<>(mucDanhGiaCuaGiangVien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MucDanhGiaCuaGiangVien> createMucDanhGiaCuaGiangVien(@RequestBody MucDanhGiaCuaGiangVienDto mucDanhGiaCuaGiangVienDto, @RequestParam Integer khoaId) {
        MucDanhGiaCuaGiangVien createdMucDanhGiaCuaGiangVien = mucDanhGiaCuaGiangVienService.createMucDanhGiaCuaGiangVien(khoaId,mucDanhGiaCuaGiangVienDto);
        if (createdMucDanhGiaCuaGiangVien != null) {
            return new ResponseEntity<>(createdMucDanhGiaCuaGiangVien, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MucDanhGiaCuaGiangVien> updateMucDanhGiaCuaGiangVien(@PathVariable Integer id, @RequestBody MucDanhGiaCuaGiangVienDto mucDanhGiaCuaGiangVienDto) {
        MucDanhGiaCuaGiangVien updatedMucDanhGiaCuaGiangVien = mucDanhGiaCuaGiangVienService.updateMucDanhGiaCuaGiangVien(id, mucDanhGiaCuaGiangVienDto);
        if (updatedMucDanhGiaCuaGiangVien != null) {
            return new ResponseEntity<>(updatedMucDanhGiaCuaGiangVien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMucDanhGiaCuaGiangVien(@PathVariable Integer id) {
        mucDanhGiaCuaGiangVienService.deleteMucDanhGiaCuaGiangVien(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
