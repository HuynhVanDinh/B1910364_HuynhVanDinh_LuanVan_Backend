package com.bezkoder.spring.security.jwt.controllers;
import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.payload.request.KhoaDto;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.services.KhoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/khoa")
public class KhoaController {
    @Autowired
    private KhoaService khoaService;

    @GetMapping
    public ResponseEntity<List<Khoa>> getAllKhoa() {
        List<Khoa> khoaList = khoaService.getAllKhoa();
        return new ResponseEntity<>(khoaList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Khoa> getKhoaById(@PathVariable Integer id) {
        Khoa khoa = khoaService.getKhoaById(id);
        if (khoa != null) {
            return new ResponseEntity<>(khoa, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MessageResponse> createKhoa(@RequestBody KhoaDto khoaDto) {
        Khoa createdKhoa = khoaService.createKhoa(khoaDto);
        return ResponseEntity.ok(new MessageResponse("Thêm khoa thành công!"));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new MessageResponse("Bạn không có quyền thực hiện thao tác này."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateKhoa(@PathVariable Integer id, @RequestBody KhoaDto khoaDto) {
        Khoa updatedKhoa = khoaService.updateKhoa(id, khoaDto);
        if (updatedKhoa != null) {
            return ResponseEntity.ok(new MessageResponse("Cập nhật khoa thành công!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<MessageResponse> updateKhoaStatus(@PathVariable Integer id, @RequestBody Integer newStatus) {
        try {
            System.out.println(newStatus);
            Khoa khoa = khoaService.getKhoaById(id);
            if (khoa != null) {
                khoa.setKhoaStatus(newStatus);
                khoaService.updateKhoaStatus(khoa);
                return ResponseEntity.ok(new MessageResponse("Cập nhật trạng thái thành công!"));
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Error updating grammar status: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteKhoa(@PathVariable Integer id) {
        khoaService.deleteKhoa(id);
        return ResponseEntity.ok(new MessageResponse("Xoá thành khoa thành công!"));
    }
}
