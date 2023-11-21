package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.DiemGiangVien;
import com.bezkoder.spring.security.jwt.payload.request.DiemGiangVienDto;
import com.bezkoder.spring.security.jwt.services.DiemGiangVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/diemgiangvien")
public class DiemGiangVienController {

    @Autowired
    private DiemGiangVienService diemGiangVienService;

    @GetMapping()
    public ResponseEntity<List<DiemGiangVien>> getAllDiemGiangVien() {
        List<DiemGiangVien> diemGiangVienList = diemGiangVienService.getAllDiem();
        return new ResponseEntity<>(diemGiangVienList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiemGiangVien> getDiemGiangVienById(@PathVariable Integer id) {
        DiemGiangVien diemGiangVien = diemGiangVienService.getDiemGiangVienById(id);
        if (diemGiangVien != null) {
            return new ResponseEntity<>(diemGiangVien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/giangvien/{maGV}")
    public ResponseEntity<List<DiemGiangVien>> getDiemGiangVienByGiangVien(@PathVariable Integer maGV) {
        List<DiemGiangVien> diemGiangVienList = diemGiangVienService.getAllDiemByGiangVien(maGV);
        return new ResponseEntity<>(diemGiangVienList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DiemGiangVien> createDiemGiangVien(@RequestBody DiemGiangVienDto diemGiangVienDto, @RequestParam Integer maSV, @RequestParam Integer maGV, @RequestParam Integer maPhieu ) {
        DiemGiangVien createdDiemGiangVien = diemGiangVienService.createDiemGiangVien(diemGiangVienDto, maPhieu, maSV, maGV );
        if (createdDiemGiangVien != null) {
            return new ResponseEntity<>(createdDiemGiangVien, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiemGiangVien> updateDiemGiangVien(@PathVariable Integer id, @RequestBody DiemGiangVienDto diemGiangVienDto) {
        DiemGiangVien updatedDiemGiangVien = diemGiangVienService.updateDiemGiangVien(id, diemGiangVienDto);
        if (updatedDiemGiangVien != null) {
            return new ResponseEntity<>(updatedDiemGiangVien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiemGiangVien(@PathVariable Integer id) {
        diemGiangVienService.deleteDiemGiangVien(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
