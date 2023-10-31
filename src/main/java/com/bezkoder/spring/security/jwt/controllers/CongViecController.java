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
    @GetMapping("/{sinhvienId}/{canboId}/{id_tuan}")
    public ResponseEntity<List<CongViec>> getCongViecBySinhVienAndCanBoAndTuan(@PathVariable Integer sinhvienId, @PathVariable Integer canboId, @PathVariable Integer id_tuan) {
        List<CongViec> congViec = congViecService.getCongViecBySinhVienAndCanBoAndTuan(sinhvienId,canboId,id_tuan);
        if (congViec != null) {
            return ResponseEntity.ok(congViec);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{sinhvienId}/{id_tuan}")
    public ResponseEntity<List<CongViec>> getCongViecBySinhVienAndTuan(@PathVariable Integer sinhvienId, @PathVariable Integer id_tuan) {
        List<CongViec> congViec = congViecService.getCongViecBySinhVienAndTuan(sinhvienId,id_tuan);
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
    @PutMapping("/duyet/{congViecId}")
    public ResponseEntity<MessageResponse> duyetCongViec(@PathVariable Integer congViecId,
                                                          @Valid @RequestBody CongViecDto congViecDto) {
        CongViec updatedCongViec = congViecService.duyetCongViec(congViecId, congViecDto);
        if (updatedCongViec != null) {
            return ResponseEntity.ok(new MessageResponse("Duyêt công việc thành công!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Duyệt không thành công."));
        }
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/capnhattiendo/{congViecId}")
    public ResponseEntity<MessageResponse> updateTienDoCongViec(@PathVariable Integer congViecId,
                                                          @Valid @RequestBody CongViecDto congViecDto,
                                                          @RequestParam Integer sinhVienId
                                                          ) {
        CongViec tienDoCongViec = congViecService.getCongViecById(congViecId);
        if(congViecDto.getTienDo() > tienDoCongViec.getTienDo() ){
            CongViec updatedCongViec = congViecService.updateTienDoCongViec(congViecId, congViecDto, sinhVienId);
            if (updatedCongViec != null) {
                return ResponseEntity.ok(new MessageResponse("Cập nhật tiến độ công việc thành công!"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy Sinh viên hoặc Cán bộ với ID tương ứng."));
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Tiến độ phải lớn hơn tiến độ trước"));
    }
    @PreAuthorize("hasRole('CADRE')")
    @DeleteMapping("/{congViecId}")
    public ResponseEntity<MessageResponse> deleteCongViec(@PathVariable Integer congViecId) {
        congViecService.deleteCongViec(congViecId);
        return ResponseEntity.ok(new MessageResponse("Xóa công việc thành công!"));
    }
}
