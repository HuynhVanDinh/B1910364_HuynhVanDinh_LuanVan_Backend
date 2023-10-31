package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.MucDanhGiaCuaCanBo;
import com.bezkoder.spring.security.jwt.entity.PhieuDiemCanbo;
import com.bezkoder.spring.security.jwt.payload.request.MucDanhGiaCuaCanBoDto;
import com.bezkoder.spring.security.jwt.payload.request.PhieuDiemCanboDto;
import com.bezkoder.spring.security.jwt.services.MucDanhGiaCuaCanBoService;
import com.bezkoder.spring.security.jwt.services.PhieuDiemCanboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mucdanhgiacuacanbo")
public class MucDanhGiaCuaCanBoController {
    @Autowired
    private MucDanhGiaCuaCanBoService mucDanhGiaCuaCanBoService;

    @GetMapping
    public ResponseEntity<List<MucDanhGiaCuaCanBo>> getAllMucDanhGiaCuaCanBo() {
        List<MucDanhGiaCuaCanBo> mucDanhGiaCuaCanBoList = mucDanhGiaCuaCanBoService.getAllMucDanhGiaCuaCanBo();
        return new ResponseEntity<>(mucDanhGiaCuaCanBoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MucDanhGiaCuaCanBo> getPhieuDiemCanboById(@PathVariable Integer id) {
        MucDanhGiaCuaCanBo mucDanhGiaCuaCanBo = mucDanhGiaCuaCanBoService.getMucDanhGiaCuaCanBoById(id);
        if (mucDanhGiaCuaCanBo != null) {
            return new ResponseEntity<>(mucDanhGiaCuaCanBo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MucDanhGiaCuaCanBo> createPhieuDiemCanbo(@RequestBody MucDanhGiaCuaCanBoDto mucDanhGiaCuaCanBoDto) {
        MucDanhGiaCuaCanBo createdMucDanhGiaCuaCanBo = mucDanhGiaCuaCanBoService.createMucDanhGiaCuaCanBo(mucDanhGiaCuaCanBoDto);
        if (createdMucDanhGiaCuaCanBo != null) {
            return new ResponseEntity<>(createdMucDanhGiaCuaCanBo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MucDanhGiaCuaCanBo> updateMucDanhGiaCuaCanBo(@PathVariable Integer id, @RequestBody MucDanhGiaCuaCanBoDto mucDanhGiaCuaCanBoDto) {
        MucDanhGiaCuaCanBo updatedMucDanhGiaCuaCanBo = mucDanhGiaCuaCanBoService.updateMucDanhGiaCuaCanBo(id, mucDanhGiaCuaCanBoDto);
        if (updatedMucDanhGiaCuaCanBo != null) {
            return new ResponseEntity<>(updatedMucDanhGiaCuaCanBo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMucDanhGiaCuaCanBo(@PathVariable Integer id) {
        mucDanhGiaCuaCanBoService.deleteMucDanhGiaCuaCanBo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
