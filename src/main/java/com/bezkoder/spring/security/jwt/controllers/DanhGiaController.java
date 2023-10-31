package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.CongViec;
import com.bezkoder.spring.security.jwt.entity.DanhGia;
import com.bezkoder.spring.security.jwt.payload.request.DanhGiaDto;
import com.bezkoder.spring.security.jwt.services.DanhGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/danhgia")
public class DanhGiaController {

    @Autowired
    private DanhGiaService danhGiaService;

    @GetMapping("/all")
    public ResponseEntity<List<DanhGia>> getAllDanhGia() {
        List<DanhGia> danhGiaList = danhGiaService.getAllDanhGia();
        return new ResponseEntity<>(danhGiaList, HttpStatus.OK);
    }

    @GetMapping("/tuan/{id_tuan}")
    public ResponseEntity<List<DanhGia>> getAllDanhGiaByTuan(@PathVariable Integer id_tuan) {
        List<DanhGia> danhGiaList = danhGiaService.getAllDanhGiaByTuan(id_tuan);
        return new ResponseEntity<>(danhGiaList, HttpStatus.OK);
    }

    @GetMapping("/sinhvien/{sinhVienId}")
    public ResponseEntity<List<DanhGia>> getAllDangGiaSinhVien(@PathVariable Integer sinhVienId) {
        List<DanhGia> danhGiaList = danhGiaService.getAllDanhGiaSinhVien(sinhVienId);
        return new ResponseEntity<>(danhGiaList, HttpStatus.OK);
    }

    @GetMapping("/canbo/{canBoId}")
    public ResponseEntity<List<DanhGia>> getAllDanhGiaCanBo(@PathVariable Integer canBoId) {
        List<DanhGia> danhGiaList = danhGiaService.getAllDangGiaCanBo(canBoId);
        return new ResponseEntity<>(danhGiaList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DanhGia> getDanhGiaById(@PathVariable Integer id) {
        DanhGia danhGia = danhGiaService.getDanhGiaById(id);
        if (danhGia != null) {
            return new ResponseEntity<>(danhGia, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{sinhvienId}/{canboId}/{id_tuan}")
    public ResponseEntity<List<DanhGia>> getDanhgiaBySinhVienAndCanBoAndTuan(@PathVariable Integer sinhvienId, @PathVariable Integer canboId, @PathVariable Integer id_tuan) {
        List<DanhGia> danhGia = danhGiaService.getDanhGiaBySinhVienAndCanBoAndTuan(sinhvienId,canboId,id_tuan);
        if (danhGia != null) {
            return ResponseEntity.ok(danhGia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasRole('CADRE')")
    @PostMapping
    public ResponseEntity<DanhGia> createDangKy(@RequestBody DanhGiaDto danhGiaDto, @RequestParam Integer tuan, @RequestParam Integer sinhVien, @RequestParam Integer canBo) {
        DanhGia createdDanhGia = danhGiaService.createDangKy(danhGiaDto, tuan, sinhVien, canBo);
        if (createdDanhGia != null) {
            return new ResponseEntity<>(createdDanhGia, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('CADRE')")
    @PutMapping("/{id}")
    public ResponseEntity<DanhGia> updateDanhGia(@PathVariable Integer id, @RequestBody DanhGiaDto danhGiaDto) {
        DanhGia updatedDanhGia = danhGiaService.updateDanhGia(id, danhGiaDto);
        if (updatedDanhGia != null) {
            return new ResponseEntity<>(updatedDanhGia, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('CADRE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhGia(@PathVariable Integer id) {
        danhGiaService.deleteDanhGia(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}