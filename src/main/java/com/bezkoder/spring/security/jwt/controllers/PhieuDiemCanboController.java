package com.bezkoder.spring.security.jwt.controllers;
import com.bezkoder.spring.security.jwt.entity.PhieuDiemCanbo;
import com.bezkoder.spring.security.jwt.payload.request.PhieuDiemCanboDto;
import com.bezkoder.spring.security.jwt.services.PhieuDiemCanboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phieudiemcanbo")
public class PhieuDiemCanboController {

    @Autowired
    private PhieuDiemCanboService phieuDiemCanboService;

    @GetMapping("/all")
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

    @PostMapping
    public ResponseEntity<PhieuDiemCanbo> createPhieuDiemCanbo(@RequestBody PhieuDiemCanboDto phieuDiemCanboDto) {
        PhieuDiemCanbo createdPhieuDiemCanbo = phieuDiemCanboService.createPhieuDiemCanbo(phieuDiemCanboDto);
        if (createdPhieuDiemCanbo != null) {
            return new ResponseEntity<>(createdPhieuDiemCanbo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhieuDiemCanbo> updatePhieuDiemCanbo(@PathVariable Integer id, @RequestBody PhieuDiemCanboDto phieuDiemCanboDto) {
        PhieuDiemCanbo updatedPhieuDiemCanbo = phieuDiemCanboService.updatePhieuDiemCanbo(id, phieuDiemCanboDto);
        if (updatedPhieuDiemCanbo != null) {
            return new ResponseEntity<>(updatedPhieuDiemCanbo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhieuDiemCanbo(@PathVariable Integer id) {
        phieuDiemCanboService.deletePhieuDiemCanbo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}