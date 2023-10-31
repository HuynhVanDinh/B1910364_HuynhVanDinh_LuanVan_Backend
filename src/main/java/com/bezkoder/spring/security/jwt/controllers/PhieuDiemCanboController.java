package com.bezkoder.spring.security.jwt.controllers;
import com.bezkoder.spring.security.jwt.entity.PhieuDiemCanbo;
import com.bezkoder.spring.security.jwt.payload.request.PhieuDiemCanboDto;
import com.bezkoder.spring.security.jwt.services.PhieuDiemCanboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/phieudiemcanbo")
public class PhieuDiemCanboController {

    @Autowired
    private PhieuDiemCanboService phieuDiemCanboService;

    @GetMapping
    public ResponseEntity<List<PhieuDiemCanbo>> getAllPhieuDiemCanbo() {
        List<PhieuDiemCanbo> phieuDiemCanboList = phieuDiemCanboService.getAllPhieuDiemCanbo();
        return new ResponseEntity<>(phieuDiemCanboList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuDiemCanbo> getPhieuDiemCanboById(@PathVariable Integer id) {
        PhieuDiemCanbo phieuDiemCanbo = phieuDiemCanboService.getPhieuDiemCanboById(id);
        if (phieuDiemCanbo != null) {
            return new ResponseEntity<>(phieuDiemCanbo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/muc/{muc_id}")
    public ResponseEntity<List<PhieuDiemCanbo>> getPhieuDiemCanboByMuc(@PathVariable Integer muc_id) {
        List<PhieuDiemCanbo> phieuDiemCanbo = phieuDiemCanboService.getPhieuDiemCanboByMuc(muc_id);
        if (phieuDiemCanbo != null) {
            return new ResponseEntity<>(phieuDiemCanbo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PhieuDiemCanbo> createPhieuDiemCanbo(@RequestBody PhieuDiemCanboDto phieuDiemCanboDto, @RequestParam Integer muc_id) {
        PhieuDiemCanbo createdPhieuDiemCanbo = phieuDiemCanboService.createPhieuDiemCanbo(phieuDiemCanboDto, muc_id);
        if (createdPhieuDiemCanbo != null) {
            return new ResponseEntity<>(createdPhieuDiemCanbo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PhieuDiemCanbo> updatePhieuDiemCanbo(@PathVariable Integer id, @RequestBody PhieuDiemCanboDto phieuDiemCanboDto, @RequestParam Integer muc_id) {
        PhieuDiemCanbo updatedPhieuDiemCanbo = phieuDiemCanboService.updatePhieuDiemCanbo(id, phieuDiemCanboDto, muc_id);
        if (updatedPhieuDiemCanbo != null) {
            return new ResponseEntity<>(updatedPhieuDiemCanbo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhieuDiemCanbo(@PathVariable Integer id) {
        phieuDiemCanboService.deletePhieuDiemCanbo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}