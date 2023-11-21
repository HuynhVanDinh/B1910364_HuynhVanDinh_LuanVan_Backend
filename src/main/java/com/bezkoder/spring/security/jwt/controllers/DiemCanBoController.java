package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.DiemCanBo;
import com.bezkoder.spring.security.jwt.payload.request.DiemCanBoDto;
import com.bezkoder.spring.security.jwt.services.DiemCanBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/diemcanbo")
public class DiemCanBoController {

    @Autowired
    private DiemCanBoService diemCanBoService;

    @GetMapping
    public ResponseEntity<List<DiemCanBo>> getAllDiemCanBo() {
        List<DiemCanBo> diemCanBoList = diemCanBoService.getAllDiem();
        return new ResponseEntity<>(diemCanBoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiemCanBo> getDiemCanBoById(@PathVariable Integer id) {
        DiemCanBo diemCanBo = diemCanBoService.getDiemCanBoById(id);
        if (diemCanBo != null) {
            return new ResponseEntity<>(diemCanBo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/canbo/{maCB}")
    public ResponseEntity<List<DiemCanBo>> getDiemCanBoByCanBo(@PathVariable Integer maCB) {
        List<DiemCanBo> diemCanBoList = diemCanBoService.getAllDiemByCanBo(maCB);
        return new ResponseEntity<>(diemCanBoList, HttpStatus.OK);
    }

    @GetMapping("/sinhvien/{maSV}")
    public ResponseEntity<List<DiemCanBo>> getDiemCanBoBySinhVien(@PathVariable Integer maSV) {
        List<DiemCanBo> diemCanBoList = diemCanBoService.getAllDiemSinhvien(maSV);
        return new ResponseEntity<>(diemCanBoList, HttpStatus.OK);
    }

    @GetMapping("/muc/{muc_id}/{maSV}")
    public ResponseEntity<DiemCanBo> getDiemCanBoByPhieuDiem(@PathVariable Integer muc_id, @PathVariable Integer maSV) {
        DiemCanBo diemCanBoList = diemCanBoService.getDiemByPhieuDiem(muc_id, maSV);
        return new ResponseEntity<>(diemCanBoList, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CADRE')")
    @PostMapping
    public ResponseEntity<DiemCanBo> createDiemCanBo(@RequestBody DiemCanBoDto diemCanBoDto, @RequestParam Integer maPhieu, @RequestParam Integer maSV, @RequestParam Integer maCB) {
        DiemCanBo createdDiemCanBo = diemCanBoService.createDiemCanBo(diemCanBoDto, maPhieu, maSV, maCB );
        if (createdDiemCanBo != null) {
            return new ResponseEntity<>(createdDiemCanBo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('CADRE')")
    @PutMapping("/{id}")
    public ResponseEntity<DiemCanBo> updateDiemCanBo(@PathVariable Integer id, @RequestBody DiemCanBoDto diemCanBoDto) {
        DiemCanBo updatedDiemCanBo = diemCanBoService.updateDiemCanBo(id, diemCanBoDto);
        if (updatedDiemCanBo != null) {
            return new ResponseEntity<>(updatedDiemCanBo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('CADRE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiemCanBo(@PathVariable Integer id) {
        diemCanBoService.deleteDiemCanBo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
