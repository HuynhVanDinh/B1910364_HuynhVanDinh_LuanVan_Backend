package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.payload.request.BaiDangDto;
import com.bezkoder.spring.security.jwt.payload.request.RegistrationStatus;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.entity.BaiDang;
import com.bezkoder.spring.security.jwt.entity.DonViThucTap;
import com.bezkoder.spring.security.jwt.services.BaiDangService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/baidang")
public class BaiDangController {

    private final BaiDangService baiDangService;

    public BaiDangController(BaiDangService baiDangService) {
        this.baiDangService = baiDangService;
    }

    @GetMapping
    public ResponseEntity<List<BaiDang>> getAllBaiDang() {
        List<BaiDang> baiDangList = baiDangService.getAllBaiDang();
        return ResponseEntity.ok(baiDangList);
    }

    @GetMapping("/{baiDangId}")
    public ResponseEntity<BaiDang> getBaiDangById(@PathVariable Integer baiDangId) {
        BaiDang baiDang = baiDangService.getBaiDangById(baiDangId);
        if (baiDang != null) {
            return ResponseEntity.ok(baiDang);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/donvi/{maDvtt}")
    public ResponseEntity<BaiDang> getBaiDangByDonViThucTap(@PathVariable Integer maDvtt) {
        BaiDang baiDang = baiDangService.getBaiDangByDonViThucTap(maDvtt);
        if (baiDang != null) {
            return ResponseEntity.ok(baiDang);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('UNIT')")
    @PostMapping
    public ResponseEntity<MessageResponse> createBaiDang(@Valid @RequestBody BaiDangDto baiDangDto,
                                                         @RequestParam Integer donViThucTapId) {

        BaiDang createdBaiDang = baiDangService.createBaiDang(baiDangDto, donViThucTapId);
        if (createdBaiDang != null) {
            return ResponseEntity.ok(new MessageResponse("Tạo bài đăng thành công!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy đơn vị thực tập với id: " + donViThucTapId));
        }
    }

    @PreAuthorize("hasRole('UNIT')")
    @PutMapping("/{baiDangId}")
    public ResponseEntity<MessageResponse> updateBaiDang(@PathVariable Integer baiDangId,
                                                         @Valid @RequestBody BaiDangDto baiDangDto,
                                                         @RequestParam Integer donViThucTapId) {
//        DonViThucTap donViThucTap = new DonViThucTap();

        BaiDang updatedBaiDang = baiDangService.updateBaiDang(baiDangId, baiDangDto, donViThucTapId);
        if (updatedBaiDang != null) {
            return ResponseEntity.ok(new MessageResponse("Cập nhật bài đăng thành công!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy bài đăng với id: " + baiDangId));
        }
    }

    @PreAuthorize("hasRole('UNIT')")
    @DeleteMapping("/{baiDangId}")
    public ResponseEntity<MessageResponse> deleteBaiDang(@PathVariable Integer baiDangId) {
        baiDangService.deleteBaiDang(baiDangId);
        return ResponseEntity.ok(new MessageResponse("Xóa bài đăng thành công!"));
    }
}
