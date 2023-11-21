package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.Tuan;
import com.bezkoder.spring.security.jwt.payload.request.TuanDto;
import com.bezkoder.spring.security.jwt.services.TuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tuan")
public class TuanController {

    @Autowired
    private TuanService tuanService;

    @GetMapping
    public ResponseEntity<List<Tuan>> getAllTuan() {
        List<Tuan> tuanList = tuanService.getAllTuan();
        return new ResponseEntity<>(tuanList, HttpStatus.OK);
    }
    @GetMapping("/canbo/{macb}")
    public ResponseEntity<List<Tuan>> getTuanCanBo(@PathVariable Integer macb) {
        List<Tuan> tuanList = tuanService.getTuanByCanbo(macb);
        return new ResponseEntity<>(tuanList, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Tuan> getTuanById(@PathVariable Integer id) {
        Tuan tuan = tuanService.getTuanById(id);
        if (tuan != null) {
            return new ResponseEntity<>(tuan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/all/{trangThai}")
    public ResponseEntity<List<Tuan>> getTuanByTrangThai(@PathVariable Integer trangThai) {
        List<Tuan> tuan = tuanService.getTuanByTrangThai(trangThai);
        if (tuan != null) {
            return new ResponseEntity<>(tuan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('CADRE')")
    @PostMapping
    public ResponseEntity<Tuan> createTuan(@RequestBody TuanDto tuanDto, @RequestParam Integer macb) {

        Tuan createdTuan = tuanService.createTuan(tuanDto, macb);
        if (createdTuan != null) {
            return new ResponseEntity<>(createdTuan, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('CADRE')")
    @PutMapping("/{id}")
    public ResponseEntity<Tuan> updateTuan(@PathVariable Integer id, @RequestBody TuanDto tuanDto) {
        Tuan updatedTuan = tuanService.updateTuan(id, tuanDto);
        if (updatedTuan != null) {
            return new ResponseEntity<>(updatedTuan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('CADRE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTuan(@PathVariable Integer id) {
        tuanService.deleteTuan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/hideAll")
    public ResponseEntity<String> hideAllTuan() {
        try {
            tuanService.hideAllTuan();
            return new ResponseEntity<>("Successfully updated tuan statuses.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update tuan statuses.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
