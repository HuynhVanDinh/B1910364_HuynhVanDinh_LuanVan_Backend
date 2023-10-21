package com.bezkoder.spring.security.jwt.controllers;

import java.util.List;
import com.bezkoder.spring.security.jwt.entity.CongViec;
import com.bezkoder.spring.security.jwt.payload.request.CongViecDto;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.services.CongViecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/congviec")
public class CongViecController {

    private final CongViecService congViecService;

    @Autowired
    public CongViecController(CongViecService congViecService) {
        this.congViecService = congViecService;
    }

    @GetMapping
    public ResponseEntity<List<CongViec>> getAllCongViec() {
        List<CongViec> congViecList = congViecService.getAllCongViec();
        return ResponseEntity.ok(congViecList);
    }

    @GetMapping("/{congViecId}")
    public ResponseEntity<CongViec> getCongViecById(@PathVariable Integer congViecId) {
        CongViec congViec = congViecService.getCongViecById(congViecId);
        if (congViec != null) {
            return ResponseEntity.ok(congViec);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasRole('CADRE')")
    @PostMapping
    public ResponseEntity<MessageResponse> createCongViec(@Valid @RequestBody CongViecDto congViecDto,
                                                          @RequestParam Integer id_tuan,
                                                          @RequestParam Integer sinhVienId,
                                                          @RequestParam Integer canBoId) {
        CongViec createdCongViec = congViecService.createCongViec(congViecDto, id_tuan, sinhVienId, canBoId);
        if (createdCongViec != null) {
            return ResponseEntity.ok(new MessageResponse("Tạo công việc thành công!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy Sinh viên hoặc Cán bộ với ID tương ứng."));
        }
    }
    @PreAuthorize("hasRole('CADRE')")
    @PutMapping("/{congViecId}")
    public ResponseEntity<MessageResponse> updateCongViec(@PathVariable Integer congViecId,
                                                          @Valid @RequestBody CongViecDto congViecDto,
                                                          @RequestParam Integer sinhVienId,
                                                          @RequestParam Integer canBoId) {
        CongViec updatedCongViec = congViecService.updateCongViec(congViecId, congViecDto, sinhVienId, canBoId);
        if (updatedCongViec != null) {
            return ResponseEntity.ok(new MessageResponse("Cập nhật công việc thành công!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy Sinh viên hoặc Cán bộ với ID tương ứng."));
        }
    }
    @PreAuthorize("hasRole('CADRE')")
    @DeleteMapping("/{congViecId}")
    public ResponseEntity<MessageResponse> deleteCongViec(@PathVariable Integer congViecId) {
        congViecService.deleteCongViec(congViecId);
        return ResponseEntity.ok(new MessageResponse("Xóa công việc thành công!"));
    }
}
