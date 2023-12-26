package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.SinhVien;
import com.bezkoder.spring.security.jwt.payload.request.SinhVienDto;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.services.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sinhvien")
public class SinhVienController {
    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public ResponseEntity<List<SinhVien>> getAllSinhVien() {
        List<SinhVien> sinhvienList = sinhVienService.getAllSinhVien();
        return new ResponseEntity<>(sinhvienList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SinhVien> getSinhVienById(@PathVariable Integer id) {
        SinhVien sinhvien = sinhVienService.getSinhVienById(id);
        if (sinhvien != null) {
            return new ResponseEntity<>(sinhvien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/account/{accountid}")
    public ResponseEntity<SinhVien> getSinhVienByAccountId(@PathVariable Integer accountid) {
        SinhVien sinhVien = sinhVienService.getSinhVienByAccountId(accountid);
        if (sinhVien != null) {
            return new ResponseEntity<>(sinhVien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/chuadangky")
    public ResponseEntity<List<SinhVien>> getSinhVienChuaDangKy() {
        List<SinhVien> sinhVienChuaDangKy = sinhVienService.getSinhVienChuaDangKy();
        return ResponseEntity.ok(sinhVienChuaDangKy);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<SinhVien>> searchSinhVien(@RequestParam String tenSV) {
        List<SinhVien> sinhVienList = sinhVienService.searchSinhVienByName(tenSV);
        return ResponseEntity.ok(sinhVienList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SinhVien> createSinhVien(@RequestBody SinhVienDto sinhVienDto,
                                                   @RequestParam Integer lopId,
                                                   @RequestParam String email
                                                  ) {
        SinhVien createdSinhVien = sinhVienService.createSinhVien(sinhVienDto, lopId, email);
        return ResponseEntity.ok(createdSinhVien);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createFromExcel")
    public ResponseEntity<?> createSinhVienFromExcel(@RequestParam("file") MultipartFile file) {
//        try {
            SinhVien sinhVien = sinhVienService.createSinhVienFromExcel(file);
            if (sinhVien != null) {
                return ResponseEntity.ok("SinhVien created successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error creating SinhVien.");
            }
//        }
//        catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Error processing the Excel file.");
//        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{sinhvienId}")
    public ResponseEntity<MessageResponse> updateSinhVien(@PathVariable Integer sinhvienId,
                                                   @RequestBody SinhVienDto sinhVienDto,
                                                   @RequestParam Integer lopId,
                                                   @RequestParam String email
    ) {
        SinhVien updatedSinhVien = sinhVienService.updateSinhVien(sinhvienId, sinhVienDto, lopId, email);
        if (updatedSinhVien != null) {
            return ResponseEntity.ok(new MessageResponse("Cập nhật sinh viên thành công!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        return ResponseEntity.ok(updatedSinhVien);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/chinhsua/{sinhvienId}")
    public ResponseEntity<MessageResponse> editSinhVien(@PathVariable Integer sinhvienId,
                                                          @RequestBody SinhVienDto sinhVienDto
    ) {
        SinhVien updatedSinhVien = sinhVienService.editSinhVien(sinhvienId, sinhVienDto);
        if (updatedSinhVien != null) {
            return ResponseEntity.ok(new MessageResponse("Cập nhật sinh viên thành công!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/{sinhvienId}/capnhatanhdien")
    public ResponseEntity<MessageResponse> updateAvt(@PathVariable Integer sinhvienId,
                                                          @RequestBody String hinhAnh

    ) {
        SinhVien updatedAvt = sinhVienService.updateAvt(sinhvienId,hinhAnh);
        if (updatedAvt != null) {
            return ResponseEntity.ok(new MessageResponse("Cập nhật ảnh diện thành công!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{sinhvienId}")
    public ResponseEntity<Void> deleteSinhVien(@PathVariable Integer sinhvienId) {
        sinhVienService.deleteSinhVien(sinhvienId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/guicanhbao")
    public ResponseEntity<Void> guicanhbaodenSinhVien() {
        sinhVienService.sendEmailToChuaDangKyStudents();
        return ResponseEntity.noContent().build();
    }

}
