package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.SinhVien;
import com.bezkoder.spring.security.jwt.payload.request.SinhVienDto;
import com.bezkoder.spring.security.jwt.services.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sinhvien")
public class SinhVienController {
    @Autowired
    private SinhVienService sinhVienService;


    @GetMapping
    public ResponseEntity<List<SinhVien>> getAllSinhVien() {
        List<SinhVien> sinhvienList = sinhVienService.getAllSinhVien();
        return new ResponseEntity<>(sinhvienList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SinhVien> getSinhVienById(@PathVariable Integer id) {
        SinhVien sinhvien = sinhVienService.getSinhVienById(id);
        if (sinhvien != null) {
            return new ResponseEntity<>(sinhvien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SinhVien> createSinhVien(@RequestBody SinhVienDto sinhVienDto,
                                                   @RequestParam Integer lopId,
                                                   @RequestParam String email
                                                  ) {
        SinhVien createdSinhVien = sinhVienService.createSinhVien(sinhVienDto, lopId, email);
        return ResponseEntity.ok(createdSinhVien);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{sinhvienId}")
    public ResponseEntity<SinhVien> updateSinhVien(@PathVariable Integer sinhvienId,
                                                   @RequestBody SinhVienDto sinhVienDto,
                                                   @RequestParam Integer lopId) {
        SinhVien updatedSinhVien = sinhVienService.updateSinhVien(sinhvienId, sinhVienDto, lopId);
        return ResponseEntity.ok(updatedSinhVien);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{sinhvienId}")
    public ResponseEntity<Void> deleteSinhVien(@PathVariable Integer sinhvienId) {
        sinhVienService.deleteSinhVien(sinhvienId);
        return ResponseEntity.noContent().build();
    }

}
