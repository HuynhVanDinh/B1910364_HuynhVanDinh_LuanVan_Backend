package com.bezkoder.spring.security.jwt.controllers;
import com.bezkoder.spring.security.jwt.entity.GiangVien;
import com.bezkoder.spring.security.jwt.payload.request.GiangVienDto;
import com.bezkoder.spring.security.jwt.services.GiangVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/giangvien")
public class GiangVienController {

    @Autowired
    private GiangVienService giangVienService;

    @GetMapping
    public List<GiangVien> getAllGiangVien() {
        return giangVienService.getAllGiangVien();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiangVien> getGiangVienById(@PathVariable Integer id) {
        GiangVien giangVien = giangVienService.getGiangVienById(id);
        if (giangVien != null) {
            return new ResponseEntity<>(giangVien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/khoa/{khoaId}")
    public ResponseEntity<List<GiangVien>> getGiangVienByKhoa(@PathVariable Integer khoaId) {
       List<GiangVien>  giangVien = giangVienService.getGiangVienByKhoa(khoaId);
        if (giangVien != null) {
            return new ResponseEntity<>(giangVien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<GiangVien> createGiangVien(@RequestBody GiangVienDto giangVienDto, @RequestParam Integer khoaId,
                                                     @RequestParam String username, @RequestParam String password, @RequestParam String email) {
        GiangVien giangVien = giangVienService.createGiangVien(giangVienDto, khoaId, username, password, email);
        if (giangVien != null) {
            return new ResponseEntity<>(giangVien, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{khoaId}")
    public ResponseEntity<GiangVien> updateGiangVien(@RequestBody GiangVienDto giangVienDto, @PathVariable Integer khoaId) {
        GiangVien updatedGiangVien = giangVienService.updateGiangVien(giangVienDto, khoaId);
        if (updatedGiangVien != null) {
            return new ResponseEntity<>(updatedGiangVien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{giangVienId}")
    public ResponseEntity<Void> deleteGiangVien(@PathVariable Integer giangVienId) {
        giangVienService.deleteGiangVien(giangVienId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
