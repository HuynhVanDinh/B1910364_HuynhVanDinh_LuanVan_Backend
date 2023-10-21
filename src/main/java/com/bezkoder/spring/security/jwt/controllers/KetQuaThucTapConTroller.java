package com.bezkoder.spring.security.jwt.controllers;
import com.bezkoder.spring.security.jwt.entity.KetQuaThucTap;
import com.bezkoder.spring.security.jwt.payload.request.KetQuaThucTapDto;
import com.bezkoder.spring.security.jwt.services.KetQuaThucTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ketquathuctap")
public class KetQuaThucTapConTroller {
    @Autowired
    private KetQuaThucTapService ketQuaThucTapService;

    @GetMapping
    public List<KetQuaThucTap> getAllKetQuaThucTap() {
        return ketQuaThucTapService.getAllKetQuaThucTap();
    }

    @GetMapping("/{id}")
    public ResponseEntity<KetQuaThucTap> getKetQuaThucTapById(@PathVariable Integer id) {
        KetQuaThucTap ketQuaThucTap = ketQuaThucTapService.getKetQuaThucTapById(id);
        if (ketQuaThucTap != null) {
            return new ResponseEntity<>(ketQuaThucTap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/madvtt/{madvtt}")
    public List<KetQuaThucTap> getKetQuaThucTapByMaDvtt(@PathVariable Integer madvtt) {
        return ketQuaThucTapService.getKetQuaThucTapByMaDvtt(madvtt);
    }
    @GetMapping("/chuaphancong/{madvtt}")
    public List<KetQuaThucTap> getKetQuaThucTapChuaPhanCong(@PathVariable Integer madvtt) {
        return ketQuaThucTapService.getKetQuaThucTapChuaPhanCong(madvtt);
    }

    @GetMapping("/magv/{magv}")
    public List<KetQuaThucTap> getKetQuaThucTapByMaGv(@PathVariable Integer magv) {
        return ketQuaThucTapService.getKetQuaThucTapByMaGv(magv);
    }

    @GetMapping("/macb/{macb}")
    public List<KetQuaThucTap> getKetQuaThucTapByMaCb(@PathVariable Integer macb) {
        return ketQuaThucTapService.getKetQuaThucTapByMaCb(macb);
    }
    @GetMapping("/masv/{masv}")
    public KetQuaThucTap getKetQuaThucTapByMaSv(@PathVariable Integer masv) {
        return ketQuaThucTapService.getKetQuaTHucTapByMaSv(masv);
    }

    @PostMapping("/{maSV}/{maDvtt}/{maGv}/{maDot}")
    public ResponseEntity<KetQuaThucTap> createKetQuaThucTap(
                                                             @PathVariable Integer maSV,
                                                             @PathVariable Integer maDvtt,
                                                             @PathVariable Integer maGv,
                                                             @PathVariable Integer maDot) {
        KetQuaThucTap ketQuaThucTap = ketQuaThucTapService.createKetQuaThucTap(
                maSV,
                maDvtt,
                maGv,
                maDot
        );
        if (ketQuaThucTap != null) {
            return new ResponseEntity<>(ketQuaThucTap, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{maDot}")
    public ResponseEntity<KetQuaThucTap> updateKetQuaThucTap(@RequestBody KetQuaThucTapDto ketQuaThucTapDto,
                                                             @PathVariable Integer maDot) {
        KetQuaThucTap updatedKetQuaThucTap = ketQuaThucTapService.updateKetQuaThucTap(
                ketQuaThucTapDto,
                maDot
        );
        if (updatedKetQuaThucTap != null) {
            return new ResponseEntity<>(updatedKetQuaThucTap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/thaydoi/{maCB}")
    public ResponseEntity<KetQuaThucTap> updateCanBo(@RequestBody Integer maSV,
                                                             @PathVariable Integer maCB) {
        KetQuaThucTap updatedKetQuaThucTap = ketQuaThucTapService.updateCanBo(
                maSV,
                maCB
        );
        if (updatedKetQuaThucTap != null) {
            return new ResponseEntity<>(updatedKetQuaThucTap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/diem/{maGv}")
    public ResponseEntity<KetQuaThucTap> updateDiemKetQuaThucTap(@RequestBody KetQuaThucTapDto ketQuaThucTapDto,
                                                                 @PathVariable Integer maGv) {
        KetQuaThucTap updatedKetQuaThucTap = ketQuaThucTapService.updateDiemKetQuaThucTap(
                ketQuaThucTapDto,
                maGv
        );
        if (updatedKetQuaThucTap != null) {
            return new ResponseEntity<>(updatedKetQuaThucTap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update-multiple")
    public ResponseEntity<List<KetQuaThucTap>> updateMultipleKetQuaThucTapWithSingleCanBo(
            @RequestBody List<KetQuaThucTap> ketQuaThucTaps,
            @RequestParam Integer maCb) {
        List<KetQuaThucTap> updatedKetQuaThucTaps = ketQuaThucTapService.updateMultipleKetQuaThucTapWithSingleCanBo(ketQuaThucTaps, maCb);
        if (!updatedKetQuaThucTaps.isEmpty()) {
            return new ResponseEntity<>(updatedKetQuaThucTaps, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{makqtt}")
    public ResponseEntity<Void> deleteKetQuaThucTap(@PathVariable Integer makqtt) {
        ketQuaThucTapService.deleteKetQuaThucTap(makqtt);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
