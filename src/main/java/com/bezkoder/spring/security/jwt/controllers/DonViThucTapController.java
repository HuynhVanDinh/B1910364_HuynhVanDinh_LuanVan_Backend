package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.DonViThucTap;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import com.bezkoder.spring.security.jwt.payload.request.DonViThucTapDto;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.services.DonViThucTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/donvithuctap")
public class DonViThucTapController {

    @Autowired
    private DonViThucTapService donViThucTapService;

    @GetMapping
    public List<DonViThucTap> getAllDonViThucTap() {
        return donViThucTapService.getAllDonViThucTap();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonViThucTap> getDonViThucTapById(@PathVariable Integer id) {
        DonViThucTap donViThucTap = donViThucTapService.getDonViThucTapById(id);
        if (donViThucTap != null) {
            return ResponseEntity.ok(donViThucTap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createDonViThucTap(@Valid @RequestBody DonViThucTapDto donViThucTapDto, @RequestParam String email) {
        DonViThucTap createdDonViThucTap = donViThucTapService.createDonViThucTap(donViThucTapDto, email);
        if (createdDonViThucTap != null) {
            return ResponseEntity.ok(new MessageResponse("Đã tạo đơn vị thực tập thành công!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy !"));
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDonViThucTap(
            @PathVariable Integer id,
            @RequestParam String email,
            @Valid @RequestBody DonViThucTapDto donViThucTapDto) {
        DonViThucTap updatedDonViThucTap = donViThucTapService.updateDonViThucTap(id, donViThucTapDto, email);
        if (updatedDonViThucTap != null) {
            return ResponseEntity.ok(new MessageResponse("Cập nhật đơn vị thực tập thành công!"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDonViThucTap(@PathVariable Integer id) {
        donViThucTapService.deleteDonViThucTap(id);
        return ResponseEntity.ok(new MessageResponse("Xóa đơn vị thực tập thành công!"));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<DonViThucTap>> searchDonVi(@RequestParam String tenDvtt) {
        List<DonViThucTap> donViThucTapList = donViThucTapService.searchDonViThucTapByName(tenDvtt);
        return ResponseEntity.ok(donViThucTapList);
    }
    @GetMapping("/account/{accountid}")
    public ResponseEntity<DonViThucTap> getDonViThucTapByAccountId(@PathVariable Integer accountid) {
        DonViThucTap dvtt = donViThucTapService.getDonViThucTapByAccountId(accountid);
        if (dvtt != null) {
            return new ResponseEntity<>(dvtt, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
