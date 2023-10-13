package com.bezkoder.spring.security.jwt.controllers;
import com.bezkoder.spring.security.jwt.entity.DonViThucTap;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.entity.CanBo;
import com.bezkoder.spring.security.jwt.services.CanBoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.bezkoder.spring.security.jwt.payload.request.CanBoDto;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/canbo")
public class CanBoController {

    @Autowired
    private CanBoService canBoService;
    @PreAuthorize("hasRole('UNIT')")
    @PostMapping
    public ResponseEntity<MessageResponse> createCanBo(@RequestBody CanBoDto canBoDto,
                                                       @RequestParam Integer donViThucTapId,
                                                       @RequestParam String username,
                                                       @RequestParam String password,
                                                       @RequestParam String email) {
        CanBo createdCanBo = canBoService.createCanBo(canBoDto, donViThucTapId, username, password, email);
        if (createdCanBo != null) {
            return ResponseEntity.ok(new MessageResponse("Tạo cán bộ thành công!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy đơn vị thực tập với id: " + donViThucTapId));
        }
    }
    @PreAuthorize("hasRole('UNIT')")
    @GetMapping("/{canBoId}")
    public ResponseEntity<CanBo> getCanBoById(@PathVariable Integer canBoId) {
        CanBo canBo = canBoService.getCanBoById(canBoId);
        if (canBo != null) {
            return ResponseEntity.ok(canBo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CanBo>> getAllCanBo() {
        List<CanBo> canBoList = canBoService.getAllCanBo();
        return ResponseEntity.ok(canBoList);
    }
    @GetMapping("/account/{accountid}")
    public ResponseEntity<CanBo> getCanBoByAccountId(@PathVariable Integer accountid) {
        CanBo canBo = canBoService.getCanBoByAccountId(accountid);
        if (canBo != null) {
            return new ResponseEntity<>(canBo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/listcanbo/{dvttid}")
    public ResponseEntity<List<CanBo>> getCanBoByDonViThucTap(@PathVariable DonViThucTap dvttid) {
        List<CanBo> canBo = canBoService.getCanBoByDonViThucTap(dvttid);
        if (canBo != null) {
            return new ResponseEntity<>(canBo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('UNIT')")
    @PutMapping("/{canBoId}")
    public ResponseEntity<MessageResponse> updateCanBo(@PathVariable Integer canBoId,
                                                       @Valid @RequestBody CanBoDto canBoDto,
                                                       @RequestParam Integer donViThucTapId) {
        CanBo updatedCanBo = canBoService.updateCanBo(canBoId, canBoDto, donViThucTapId);
        if (updatedCanBo != null) {
            return ResponseEntity.ok(new MessageResponse("Cập nhật cán bộ thành công!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy đơn vị thực tập với id: " + donViThucTapId));
        }
    }
    @PreAuthorize("hasRole('UNIT')")
    @DeleteMapping("/{canBoId}")
    public ResponseEntity<MessageResponse> deleteCanBo(@PathVariable Integer canBoId) {
        canBoService.deleteCanBo(canBoId);
        return ResponseEntity.ok(new MessageResponse("Xóa cán bộ thành công!"));
    }
}
