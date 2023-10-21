package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.payload.request.DangKyDto;
import com.bezkoder.spring.security.jwt.entity.DangKy;
import com.bezkoder.spring.security.jwt.entity.BaiDang;
import com.bezkoder.spring.security.jwt.entity.SinhVien;
import com.bezkoder.spring.security.jwt.payload.request.RegistrationStatus;
import com.bezkoder.spring.security.jwt.repository.DangKyRepository;
import com.bezkoder.spring.security.jwt.services.DangKyService;
import com.bezkoder.spring.security.jwt.services.BaiDangService;
import com.bezkoder.spring.security.jwt.services.SinhVienService;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dangky")
public class DangKyController {

    @Autowired
    private DangKyService dangKyService;

    @Autowired
    private BaiDangService baiDangService;

    @Autowired
    private SinhVienService sinhVienService;
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping
    public ResponseEntity<MessageResponse> createDangKy(@Valid @RequestBody DangKyDto dangKyDto,
                                                        @RequestParam Integer baiDangId,
                                                        @RequestParam Integer sinhVienId) {
        BaiDang baiDang = baiDangService.getBaiDangById(baiDangId);
        SinhVien sinhVien = sinhVienService.getSinhVienById(sinhVienId);

        if (baiDang != null && sinhVien != null) {
            DangKy createdDangKy = dangKyService.createDangKy(dangKyDto, baiDangId, sinhVienId);
            if (createdDangKy != null) {
                return ResponseEntity.ok(new MessageResponse("Đăng ký thành công!"));
            } else {
                if(baiDang.getSoLuong()==0){
                    return ResponseEntity.badRequest().body(new MessageResponse("Xin lỗi...Số lượng không đủ"));
                }
                return ResponseEntity.badRequest().body(new MessageResponse("Đăng ký không thành công. Sinh viên đã đăng ký bài đăng này trước đó."));
            }
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy bài đăng hoặc sinh viên tương ứng."));
        }
    }
    @GetMapping("/check-registration")
    public ResponseEntity<RegistrationStatus> checkRegistration(
            @RequestParam Integer sinhVienId,
            @RequestParam Integer baiDangId
    ) {
        boolean isRegistered = dangKyService.isBaiDangRegistered(sinhVienId, baiDangId);
        return ResponseEntity.ok(new RegistrationStatus(isRegistered));
    }

    @GetMapping("/{dangKyId}")
    public ResponseEntity<DangKy> getDangKyById(@PathVariable Integer dangKyId) {
        DangKy dangKy = dangKyService.getDangKyById(dangKyId);
        if (dangKy != null) {
            return ResponseEntity.ok(dangKy);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{sinhVienId}/baidangdadangky")
    public ResponseEntity<List<DangKy>> getBaiDangDaDangKyCuaSinhVien(@PathVariable Integer sinhVienId) {
        List<DangKy> danhSachDangKy = dangKyService.getBaiDangDaDangKyCuaSinhVien(sinhVienId);
        if (danhSachDangKy != null && !danhSachDangKy.isEmpty()) {
            return new ResponseEntity<>(danhSachDangKy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{sinhVienId}/baidangdaduyet")
    public ResponseEntity<List<DangKy>> getBaiDangDaDuyetCuaSinhVien(@PathVariable Integer sinhVienId) {
        List<DangKy> danhSachDangKy = dangKyService.getBaiDangDaDangKyCuaSinhVienDaDuyet(sinhVienId);
        if (danhSachDangKy != null && !danhSachDangKy.isEmpty()) {
            return new ResponseEntity<>(danhSachDangKy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<DangKy>> getAllDangKy() {
        List<DangKy> dangKyList = dangKyService.getAllDangKy();
        return ResponseEntity.ok(dangKyList);
    }
    @GetMapping("/{maDvtt}/dangky")
    public ResponseEntity<List<DangKy>> getAllDangKyByMaDvtt(@PathVariable Integer maDvtt) {
        List<DangKy> danhSachDangKy = dangKyService.getAllDangKyByMaDvtt(maDvtt);
        if (!danhSachDangKy.isEmpty()) {
            return new ResponseEntity<>(danhSachDangKy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/{dangKyId}")
    public ResponseEntity<MessageResponse> deleteDangKy(@PathVariable Integer dangKyId) {
        dangKyService.deleteDangKy(dangKyId);
        return ResponseEntity.ok(new MessageResponse("Xóa đăng ký thành công!"));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/{dangKyId}")
    public ResponseEntity<MessageResponse> updateDangKy(@PathVariable Integer dangKyId,
                                                        @Valid @RequestBody DangKyDto dangKyDto,
                                                        @RequestParam Integer baiDangId,
                                                        @RequestParam Integer sinhVienId) {
//        BaiDang baiDang = baiDangService.getBaiDangById(baiDangId);
//        SinhVien sinhVien = sinhVienService.getSinhVienById(sinhVienId);
//
//        if (baiDang != null && sinhVien != null) {
            DangKy updatedDangKy = dangKyService.updateDangKy(dangKyId, dangKyDto, baiDangId, sinhVienId);
            if (updatedDangKy != null) {
                return ResponseEntity.ok(new MessageResponse("Cập nhật đăng ký thành công!"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Cập nhật đăng ký không thành công. Đăng ký không tồn tại hoặc không hợp lệ."));
            }
//        } else {
//            return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy bài đăng hoặc sinh viên tương ứng."));
//        }
    }
    @PutMapping("/{maDK}/capnhattrangthai")
    public ResponseEntity<MessageResponse> capNhatTrangThaiDangKy(@PathVariable Integer maDK,@RequestBody Integer baiDangId, @RequestParam Integer trangThaiMoi) {
        DangKy dangKy = dangKyService.updateTrangThaiDangKy(maDK, baiDangId, trangThaiMoi);
        if (dangKy != null) {
            return ResponseEntity.ok(new MessageResponse("Cập nhật đăng ký thành công!"));
        } else {
            BaiDang baiDang = baiDangService.getBaiDangById(baiDangId);
            if(baiDang.getSoLuong() == 0){
                return ResponseEntity.badRequest().body(new MessageResponse("Không thể tiếp nhận. Đã đủ số lượng"));
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy mã đăng ký"));
        }
    }

}
