package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.entity.KetQuaThucTap;
import com.bezkoder.spring.security.jwt.services.DonViThucTapService;
import com.bezkoder.spring.security.jwt.services.KetQuaThucTapService;
import com.bezkoder.spring.security.jwt.services.KhoaService;
import com.bezkoder.spring.security.jwt.services.LopService;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/excel")
public class ExcelController {
    @Autowired
    private KhoaService khoaService;
    @Autowired
    private LopService lopService;
    @Autowired
    private DonViThucTapService donviService;
    @Autowired
    private KetQuaThucTapService ketQuaThucTapService;
    @GetMapping("/ketquathuctap")
    public void displayKetquaThuctapExcel(
            HttpServletResponse response,
            @RequestParam(name = "keyword", required = false) String keyword
    ) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=ketquathuctap.xlsx");

        List<KetQuaThucTap> ketQuaThucTapList;

        if (keyword != null && !keyword.isEmpty()) {
            // Filter by keyword (student name or student ID) if the parameter is provided
            ketQuaThucTapList = ketQuaThucTapService.searchKetQuaThucTap(keyword);
        } else {
            // Otherwise, get all results
            ketQuaThucTapList = ketQuaThucTapService.getAllKetQuaThucTap();
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("KetQuaThucTap");

        String mainTitle = "DANH SÁCH KẾT QUẢ THỰC TẬP SINH VIÊN";
        Row mainTitleRow = sheet.createRow(0);
        Cell mainTitleCell = mainTitleRow.createCell(0);
        mainTitleCell.setCellValue(mainTitle);
        mainTitleCell.setCellStyle(getMainTitleCellStyle(workbook));

        int lastColumnIndex = 3;
        CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, lastColumnIndex);
        sheet.addMergedRegion(mergedRegion);

        int titleWidth = 300;
        sheet.setColumnWidth(0, titleWidth);

        Row headerRow = sheet.createRow(1);
        headerRow.createCell(0).setCellValue("Mã sinh viên");
        headerRow.createCell(1).setCellValue("Tên sinh viên");
        headerRow.createCell(2).setCellValue("Lớp");
        headerRow.createCell(3).setCellValue("Điểm số");

        Font redFont = workbook.createFont();
        redFont.setColor(IndexedColors.RED.getIndex());

        CellStyle redFontCellStyle = workbook.createCellStyle();
        redFontCellStyle.setFont(redFont);
        int rowNum = 1;
        for (KetQuaThucTap ketqua : ketQuaThucTapList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ketqua.getSinhVien().getMaSV());
            row.createCell(1).setCellValue(ketqua.getSinhVien().getTenSV());
            row.createCell(2).setCellValue(ketqua.getSinhVien().getLop().getTenLop());
//            row.createCell(3).setCellValue((ketqua.getDiem() != null) ? ketqua.getDiem() : 0.0);
            Cell cell = row.createCell(3);
            if (ketqua.getDiem() != null) {
                cell.setCellValue(ketqua.getDiem());
            } else {
                cell.setCellValue(0.0);
                // Set the cell style for red fill color
                cell.setCellStyle(redFontCellStyle);
            }
        }
//        for (int i = 0; i < 4; i++) {
//            sheet.autoSizeColumn(i);
//        }
        for (int i = 0; i <= lastColumnIndex; i++) {
            sheet.autoSizeColumn(i);
        }
        try (OutputStream os = response.getOutputStream()) {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }
    @GetMapping("/ketquathuctap/{maGv}")
    public void displayKetquaThuctapByGiangVienExcel(
            HttpServletResponse response,
            @RequestParam(name = "keyword", required = false) String keyword,
            @PathVariable Integer maGv
    ) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=ketquathuctap.xlsx");

        List<KetQuaThucTap> ketQuaThucTapList;

        if (keyword != null && !keyword.isEmpty()) {
            // Filter by keyword (student name or student ID) if the parameter is provided
            ketQuaThucTapList = ketQuaThucTapService.searchKetQuaThucTapByGiangVien(keyword,maGv);
        } else {
            // Otherwise, get all results
            ketQuaThucTapList = ketQuaThucTapService.getKetQuaThucTapByMaGv(maGv,3);
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("KetQuaThucTap");

        String mainTitle = "DANH SÁCH KẾT QUẢ THỰC TẬP SINH VIÊN";
        Row mainTitleRow = sheet.createRow(0);
        Cell mainTitleCell = mainTitleRow.createCell(0);
        mainTitleCell.setCellValue(mainTitle);
        mainTitleCell.setCellStyle(getMainTitleCellStyle(workbook));

        int lastColumnIndex = 3;
        CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, lastColumnIndex);
        sheet.addMergedRegion(mergedRegion);

        int titleWidth = 300;
        sheet.setColumnWidth(0, titleWidth);

        Row headerRow = sheet.createRow(1);
        headerRow.createCell(0).setCellValue("Mã sinh viên");
        headerRow.createCell(1).setCellValue("Tên sinh viên");
        headerRow.createCell(2).setCellValue("Lớp");
        headerRow.createCell(3).setCellValue("Điểm số");

        Font redFont = workbook.createFont();
        redFont.setColor(IndexedColors.RED.getIndex());

        CellStyle redFontCellStyle = workbook.createCellStyle();
        redFontCellStyle.setFont(redFont);
        int rowNum = 1;
        for (KetQuaThucTap ketqua : ketQuaThucTapList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ketqua.getSinhVien().getMaSV());
            row.createCell(1).setCellValue(ketqua.getSinhVien().getTenSV());
            row.createCell(2).setCellValue(ketqua.getSinhVien().getLop().getTenLop());
//            row.createCell(3).setCellValue((ketqua.getDiem() != null) ? ketqua.getDiem() : 0.0);
            Cell cell = row.createCell(3);
            if (ketqua.getDiem() != null) {
                cell.setCellValue(ketqua.getDiem());
            } else {
                cell.setCellValue(0.0);
                // Set the cell style for red fill color
                cell.setCellStyle(redFontCellStyle);
            }
        }
//        for (int i = 0; i < 4; i++) {
//            sheet.autoSizeColumn(i);
//        }
        for (int i = 0; i <= lastColumnIndex; i++) {
            sheet.autoSizeColumn(i);
        }
        try (OutputStream os = response.getOutputStream()) {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }
    private CellStyle getMainTitleCellStyle(Workbook workbook) {
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);

        CellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return titleCellStyle;
    }
}
