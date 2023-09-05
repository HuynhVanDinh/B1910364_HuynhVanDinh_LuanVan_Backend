package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.Lop;
import com.bezkoder.spring.security.jwt.payload.request.LopDto;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.services.LopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/lop")
public class LopController {
    @Autowired
    private LopService lopService;

    @GetMapping
    public ResponseEntity<List<Lop>> getAllLop() {
        List<Lop> lopList = lopService.getAllLop();
        return new ResponseEntity<>(lopList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lop> getLopById(@PathVariable Integer id) {
        Lop lop = lopService.getLopById(id);
        if (lop != null) {
            return new ResponseEntity<>(lop, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MessageResponse> createLop(@RequestBody LopDto lopDto, @RequestParam Integer khoaId) {
        Lop createdLop = lopService.createLop(lopDto, khoaId);
        if (createdLop == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Không tìm thấy khoa với id khoa: " + khoaId));
        }

        return ResponseEntity.ok(new MessageResponse("Thêm lớp thành công!"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new MessageResponse("Bạn không có quyền thực hiện thao tác này."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{lopId}")
    public ResponseEntity<MessageResponse> updateLop(
            @PathVariable Integer lopId,
            @RequestBody LopDto lopDto,
            @RequestParam Integer khoaId) {
        Lop updatedLop = lopService.updateLop(lopId, lopDto, khoaId);
        if (updatedLop != null) {
            return ResponseEntity.ok(new MessageResponse("Cập nhật lớp thành công!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteLop(@PathVariable Integer id) {
        lopService.deleteLop(id);
        return ResponseEntity.ok(new MessageResponse("Xoá thành lớp thành công!"));
    }
}
