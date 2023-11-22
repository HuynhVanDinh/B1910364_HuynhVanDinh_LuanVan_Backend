package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.DotThucTap;
import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.Tuan;
import com.bezkoder.spring.security.jwt.payload.request.DotThucTapDto;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.services.DotThucTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dotthuctap")
public class DotThucTapController {

    @Autowired
    private DotThucTapService dotThucTapService;

    @GetMapping
    public List<DotThucTap> getAllDotThucTap() {
        return dotThucTapService.getAllDotThucTap();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DotThucTap> getDotThucTapById(@PathVariable Integer id) {
        DotThucTap dotThucTap = dotThucTapService.getDotThucTapById(id);
        if (dotThucTap != null) {
            return ResponseEntity.ok(dotThucTap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<DotThucTap>> searchKhoa(@RequestParam(required = false)  String tenDot, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate thoiGianBatDau, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate thoiGianKetThuc) {
        List<DotThucTap> dotThuctapList = dotThucTapService.searchDotThucTapByName(tenDot, thoiGianBatDau, thoiGianKetThuc);
        return ResponseEntity.ok(dotThuctapList);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createDotThucTap(@Valid @RequestBody DotThucTapDto dotThucTapDto) {

        try {
            DotThucTap createdDotThucTap = dotThucTapService.createDotThucTap(dotThucTapDto);
            if (createdDotThucTap != null) {
                return ResponseEntity.ok(new MessageResponse("Đã tạo đợt thực tập thành công!"));
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(new MessageResponse(e.getReason()), e.getStatus());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDotThucTap(
            @PathVariable Integer id,
            @Valid @RequestBody DotThucTapDto dotThucTapDto) {

        try {
            DotThucTap updatedDotThucTap = dotThucTapService.updateDotThucTap(id, dotThucTapDto);
            if (updatedDotThucTap != null) {
                return ResponseEntity.ok(new MessageResponse("Cập nhật đợt thực tập thành công!"));
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(new MessageResponse(e.getReason()), e.getStatus());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDotThucTap(@PathVariable Integer id) {
        dotThucTapService.deleteDotThucTap(id);
        return ResponseEntity.ok(new MessageResponse("Xóa đợt thực tập thành công!"));
    }
}
