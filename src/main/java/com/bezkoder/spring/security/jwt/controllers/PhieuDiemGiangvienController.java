package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.PhieuDiemGiangvien;
import com.bezkoder.spring.security.jwt.payload.request.PhieuDiemGiangvienDto;
import com.bezkoder.spring.security.jwt.services.PhieuDiemGiangvienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phieudiemgiangvien")
public class PhieuDiemGiangvienController {

    @Autowired
    private PhieuDiemGiangvienService phieuDiemGiangvienService;

    @GetMapping("/all")
    public ResponseEntity<List<PhieuDiemGiangvien>> getAllPhieuDiemGiangvien() {
        List<PhieuDiemGiangvien> phieuDiemGiangvienList = phieuDiemGiangvienService.getAllPhieuDiemGiangvien();
        return new ResponseEntity<>(phieuDiemGiangvienList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuDiemGiangvien> getPhieuDiemGiangvienById(@PathVariable Integer id) {
        PhieuDiemGiangvien phieuDiemGiangvien = phieuDiemGiangvienService.getPhieuDiemGiangvienById(id);
        if (phieuDiemGiangvien != null) {
            return new ResponseEntity<>(phieuDiemGiangvien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/khoa/{khoaId}")
    public ResponseEntity<List<PhieuDiemGiangvien>> getPhieuDiemGiangvienByKhoa(@PathVariable Integer khoaId) {
        List<PhieuDiemGiangvien> phieuDiemGiangvienList = phieuDiemGiangvienService.getPhieuDiemGiangvienByKhoa(khoaId);
        return new ResponseEntity<>(phieuDiemGiangvienList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PhieuDiemGiangvien> createPhieuDiemGiangvien(@RequestBody PhieuDiemGiangvienDto phieuDiemGiangvienDto, @RequestParam Integer khoaId) {
        PhieuDiemGiangvien createdPhieuDiemGiangvien = phieuDiemGiangvienService.createPhieuDiemGiangvien(phieuDiemGiangvienDto, khoaId);
        if (createdPhieuDiemGiangvien != null) {
            return new ResponseEntity<>(createdPhieuDiemGiangvien, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhieuDiemGiangvien> updatePhieuDiemGiangvien(@PathVariable Integer id, @RequestBody PhieuDiemGiangvienDto phieuDiemGiangvienDto) {
        PhieuDiemGiangvien updatedPhieuDiemGiangvien = phieuDiemGiangvienService.updatePhieuDiemGiangvien(id, phieuDiemGiangvienDto);
        if (updatedPhieuDiemGiangvien != null) {
            return new ResponseEntity<>(updatedPhieuDiemGiangvien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhieuDiemGiangvien(@PathVariable Integer id) {
        phieuDiemGiangvienService.deletePhieuDiemGiangvien(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}