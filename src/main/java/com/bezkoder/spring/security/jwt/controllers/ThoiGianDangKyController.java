package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.DangKy;
import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.ThoiGianDangKy;
import com.bezkoder.spring.security.jwt.payload.request.ThoiGianDangKyDto;
import com.bezkoder.spring.security.jwt.services.KhoaService;
import com.bezkoder.spring.security.jwt.services.ThoiGianDangKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/thoigiandangky")
public class ThoiGianDangKyController {

    @Autowired
    private ThoiGianDangKyService thoiGianDangKyService;
    @Autowired
    private KhoaService khoaService;


    @GetMapping
    public ResponseEntity<List<ThoiGianDangKy>> getAllThoiGianDangKy() {
        List<ThoiGianDangKy> thoiGianDangKyList = thoiGianDangKyService.getAllThoiGianDangKy();
        return new ResponseEntity<>(thoiGianDangKyList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThoiGianDangKy> getThoiGianDangKyById(@PathVariable Integer id) {
        ThoiGianDangKy thoiGianDangKy = thoiGianDangKyService.getThoiGianDangKyById(id);
        if (thoiGianDangKy != null) {
            return new ResponseEntity<>(thoiGianDangKy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/khoa")
    public ResponseEntity<ThoiGianDangKy> getThoiGianDangKyByKhoa(@RequestParam Integer khoaId) {
        Khoa khoa = khoaService.getKhoaById(khoaId);
        if (khoa != null) {
            ThoiGianDangKy thoiGianDangKyList = thoiGianDangKyService.getThoiGianDangKyByKhoa(khoa);
            return new ResponseEntity<>(thoiGianDangKyList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ThoiGianDangKy> createThoiGianDangKy(@RequestBody ThoiGianDangKyDto thoiGianDangKyDto, @RequestParam Integer khoaid) {

        ThoiGianDangKy createdThoiGianDangKy = thoiGianDangKyService.createThoiGianDangky(thoiGianDangKyDto, khoaid);
        if (createdThoiGianDangKy != null) {
            return new ResponseEntity<>(createdThoiGianDangKy, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ThoiGianDangKy> updateThoiGianDangKy(@PathVariable Integer id, @RequestBody ThoiGianDangKyDto thoiGianDangKyDto) {
        ThoiGianDangKy updatedThoiGianDangKy = thoiGianDangKyService.updateThoiGianDangKy(id, thoiGianDangKyDto);
        if (updatedThoiGianDangKy != null) {
            return new ResponseEntity<>(updatedThoiGianDangKy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteThoiGianDangKy(@PathVariable Integer id) {
        thoiGianDangKyService.deleteThoiGianDangKy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

