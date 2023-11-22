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

    @GetMapping("/magv/{magv}/{trangThai}")
    public List<KetQuaThucTap> getKetQuaThucTapByMaGv(@PathVariable Integer magv,@PathVariable Integer trangThai) {
        return ketQuaThucTapService.getKetQuaThucTapByMaGv(magv, trangThai);
    }

    @GetMapping("/macb/{macb}/{trangThai}")
    public List<KetQuaThucTap> getKetQuaThucTapByMaCb(@PathVariable Integer macb,@PathVariable Integer trangThai) {
        return ketQuaThucTapService.getKetQuaThucTapByMaCb(macb, trangThai);
    }
    @GetMapping("/macb/{macb}")
    public List<KetQuaThucTap> getKetQuaThucTapByMaCb(@PathVariable Integer macb) {
        return ketQuaThucTapService.getAllKetQuaThucTapByMaCb(macb);
    }
    @GetMapping("/masv/{masv}")
    public KetQuaThucTap getKetQuaThucTapByMaSv(@PathVariable Integer masv) {
        return ketQuaThucTapService.getKetQuaTHucTapByMaSv(masv);
    }

    @GetMapping("/dot/{maDot}")
    public List<KetQuaThucTap> getKetQuaThucTapByDot(@PathVariable Integer maDot) {
        return ketQuaThucTapService.getKetQuaThucTapByDot(maDot);
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

    @PostMapping("/phancongcoquan/{maSV}/{maDvtt}/{maGv}/{maDot}")
    public ResponseEntity<KetQuaThucTap> KhoaPhanKetQuaThucTap(
            @PathVariable Integer maSV,
            @PathVariable Integer maDvtt,
            @PathVariable Integer maGv,
            @PathVariable Integer maDot) {
        KetQuaThucTap ketQuaThucTap = ketQuaThucTapService.KhoaPhanKetQuaThucTap(
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
    @PutMapping("/thaydoitrangthai")
    public ResponseEntity<KetQuaThucTap> updateTrangThai(@RequestBody KetQuaThucTapDto ketQuaThucTapDto
                                                                 ) {
        KetQuaThucTap updatedKetQuaThucTap = ketQuaThucTapService.updateTrangThai(
                ketQuaThucTapDto

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
