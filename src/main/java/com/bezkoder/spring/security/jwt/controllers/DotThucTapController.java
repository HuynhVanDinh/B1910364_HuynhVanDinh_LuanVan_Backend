package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.DotThucTap;
import com.bezkoder.spring.security.jwt.payload.request.DotThucTapDto;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.services.DotThucTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @PostMapping
    public ResponseEntity<?> createDotThucTap(@Valid @RequestBody DotThucTapDto dotThucTapDto) {
        DotThucTap createdDotThucTap = dotThucTapService.createDotThucTap(dotThucTapDto);
        return ResponseEntity.ok(new MessageResponse("Đã tạo đợt thực tập thành công!"));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDotThucTap(
            @PathVariable Integer id,
            @Valid @RequestBody DotThucTapDto dotThucTapDto) {
        DotThucTap updatedDotThucTap = dotThucTapService.updateDotThucTap(id, dotThucTapDto);
        if (updatedDotThucTap != null) {
            return ResponseEntity.ok(new MessageResponse("Cập nhật đợt thực tập thành công!"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDotThucTap(@PathVariable Integer id) {
        dotThucTapService.deleteDotThucTap(id);
        return ResponseEntity.ok(new MessageResponse("Xóa đợt thực tập thành công!"));
    }
}
