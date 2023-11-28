package com.bezkoder.spring.security.jwt.controllers;
import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.services.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    @Autowired
    private KhoaService khoaService;
    @Autowired
    private LopService lopService;
    @Autowired
    private DonViThucTapService donviService;
    @Autowired
    private KetQuaThucTapService ketQuaThucTapService;
    @Autowired
    private SinhVienService sinhVienService;
    @Autowired
    private GiangVienService giangVienService;
//    @GetMapping("/khoa")
//    public void exportKhoaToPdf(HttpServletResponse response) throws IOException, DocumentException {
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=khoa.pdf");
//
//        List<Khoa> khoaList = khoaService.getAllKhoa();
//
//        Document document = new Document();
//        PdfWriter.getInstance(document, response.getOutputStream());
//        document.open();
//
//        for (Khoa khoa : khoaList) {
//            document.add(new Paragraph("ID: " + khoa.getId()));
//            document.add(new Paragraph("Tên Khoa: " + khoa.getTenKhoa()));
////            document.add(new Paragraph("Mô tả: " + khoa.getMoTa()));
//            document.add(new Paragraph("\n"));
//        }
//
//        document.close();
//    }
//    @GetMapping("/khoa")
//    public void displayKhoaPdf(HttpServletResponse response) throws IOException, DocumentException {
//        response.setContentType("application/pdf");
//
//        List<Khoa> khoaList = khoaService.getAllKhoa();
//
//        Document document = new Document();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PdfWriter.getInstance(document, baos);
//        document.open();
//
//        for (Khoa khoa : khoaList) {
//            document.add(new Paragraph("ID: " + khoa.getId()));
//            document.add(new Paragraph("Tên Khoa: " + khoa.getTenKhoa()));
//            document.add(new Paragraph("\n"));
//        }
//
//        document.close();
//
//        byte[] pdfBytes = baos.toByteArray();
//        response.setContentLength(pdfBytes.length);
//        OutputStream os = response.getOutputStream();
//        os.write(pdfBytes);
//        os.close();
//    }
    @GetMapping("/khoa")
    public void displayKhoaPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf; charset=UTF-8");
        String fontPath = "src/main/resources/times.ttf";
        List<Khoa> khoaList = khoaService.getAllKhoa();

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        // Tạo font và định dạng
//        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
//        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        BaseFont customBaseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(customBaseFont, 18, Font.NORMAL, BaseColor.BLACK);
        Font contentFont = new Font(customBaseFont, 12, Font.NORMAL, BaseColor.DARK_GRAY);



//        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);

        // Định dạng tiêu đề
        Paragraph title = new Paragraph("DANH SÁCH CÁC KHOA", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Định dạng nội dung
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        // Tiêu đề cho từng cột
        PdfPCell cell1 = new PdfPCell(new Phrase("Mã Khoa", contentFont));
        PdfPCell cell2 = new PdfPCell(new Phrase("Tên Khoa", contentFont));
        PdfPCell cell3 = new PdfPCell(new Phrase("Số điện thoại", contentFont));
        // Đặt kích thước và định dạng cho cột
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);



        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        // Thêm dữ liệu từ danh sách các khoa
        for (Khoa khoa : khoaList) {
            PdfPCell cellMaKhoa = new PdfPCell(new Phrase(String.valueOf(khoa.getId()), contentFont));
            PdfPCell cellTenKhoa = new PdfPCell(new Phrase(khoa.getTenKhoa(), contentFont));
            PdfPCell cellSdt = new PdfPCell(new Phrase(khoa.getSdt(), contentFont));

            cellMaKhoa.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTenKhoa.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSdt.setHorizontalAlignment(Element.ALIGN_CENTER);


            table.addCell(cellMaKhoa);
            table.addCell(cellTenKhoa);
            table.addCell(cellSdt);
        }
        document.add(new Paragraph("\n"));
        document.add(table);

        document.close();

        byte[] pdfBytes = baos.toByteArray();
        response.setContentLength(pdfBytes.length);
        OutputStream os = response.getOutputStream();
        os.write(pdfBytes);
        os.close();
    }

    @GetMapping("/lop")
    public void displayLopPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf; charset=UTF-8");
        String fontPath = "src/main/resources/times.ttf";
        List<Lop> LopList = lopService.getAllLop();

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        // Tạo font và định dạng
//        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
//        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        BaseFont customBaseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(customBaseFont, 18, Font.NORMAL, BaseColor.BLACK);
        Font contentFont = new Font(customBaseFont, 12, Font.NORMAL, BaseColor.DARK_GRAY);



//        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);

        // Định dạng tiêu đề
        Paragraph title = new Paragraph("DANH SÁCH CÁC LỚP", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Định dạng nội dung
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        // Tiêu đề cho từng cột
        PdfPCell cell1 = new PdfPCell(new Phrase("Mã Lớp", contentFont));
        PdfPCell cell2 = new PdfPCell(new Phrase("Tên Lớp", contentFont));
//        PdfPCell cell3 = new PdfPCell(new Phrase("khoa", contentFont));
        // Đặt kích thước và định dạng cho cột
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);



        table.addCell(cell1);
        table.addCell(cell2);
//        table.addCell(cell3);

        // Thêm dữ liệu từ danh sách các khoa
        for (Lop lop : LopList) {
            PdfPCell cellMaLop = new PdfPCell(new Phrase(String.valueOf(lop.getLopId()), contentFont));
            PdfPCell cellTenLop = new PdfPCell(new Phrase(lop.getTen(), contentFont));
//            PdfPCell cellKhoa = new PdfPCell(new Phrase(lop.getKhoa(), contentFont));

            cellMaLop.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTenLop.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cellKhoa.setHorizontalAlignment(Element.ALIGN_CENTER);


            table.addCell(cellMaLop);
            table.addCell(cellTenLop);
//            table.addCell(cellKhoa);
        }
        document.add(new Paragraph("\n"));
        document.add(table);

        document.close();

        byte[] pdfBytes = baos.toByteArray();
        response.setContentLength(pdfBytes.length);
        OutputStream os = response.getOutputStream();
        os.write(pdfBytes);
        os.close();
    }
    @GetMapping("/donvi")
    public void displayDonViPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf; charset=UTF-8");
        String fontPath = "src/main/resources/times.ttf";
        List<DonViThucTap> DonViThucTapList = donviService.getAllDonViThucTap();

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        // Tạo font và định dạng
        BaseFont customBaseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(customBaseFont, 18, Font.NORMAL, BaseColor.BLACK);
        Font contentFont = new Font(customBaseFont, 12, Font.NORMAL, BaseColor.DARK_GRAY);

        // Định dạng tiêu đề
        Paragraph title = new Paragraph("DANH SÁCH CÁC ĐƠN VỊ", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Định dạng nội dung
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        // Tiêu đề cho từng cột
        PdfPCell cell1 = new PdfPCell(new Phrase("Mã đơn vị", contentFont));
        PdfPCell cell2 = new PdfPCell(new Phrase("Tên đơn vị", contentFont));
        PdfPCell cell3 = new PdfPCell(new Phrase("Số điện thoại", contentFont));
        PdfPCell cell4 = new PdfPCell(new Phrase("Địa chỉ", contentFont));

        // Đặt kích thước và định dạng cho cột
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);


        // Thêm dữ liệu từ danh sách các khoa
        for (DonViThucTap donvi : DonViThucTapList) {
            PdfPCell cellMaDv = new PdfPCell(new Phrase(String.valueOf(donvi.getMaDvtt()), contentFont));
            PdfPCell cellTenDv = new PdfPCell(new Phrase(donvi.getTenDvtt(), contentFont));
            PdfPCell cellDiaChi = new PdfPCell(new Phrase(donvi.getDiaChi(), contentFont));
            PdfPCell cellSoDt = new PdfPCell(new Phrase(donvi.getSoDt(), contentFont));


            cellMaDv.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTenDv.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellDiaChi.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSoDt.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(cellMaDv);
            table.addCell(cellTenDv);
            table.addCell(cellSoDt);
            table.addCell(cellDiaChi);

        }
        document.add(new Paragraph("\n"));
        document.add(table);

        document.close();

        byte[] pdfBytes = baos.toByteArray();
        response.setContentLength(pdfBytes.length);
        OutputStream os = response.getOutputStream();
        os.write(pdfBytes);
        os.close();
    }
    @GetMapping("/giangvien")
    public void displayGiangVienPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf; charset=UTF-8");
        String fontPath = "src/main/resources/times.ttf";
        List<GiangVien> GiangVienList = giangVienService.getAllGiangVien();

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        // Tạo font và định dạng
        BaseFont customBaseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(customBaseFont, 18, Font.NORMAL, BaseColor.BLACK);
        Font contentFont = new Font(customBaseFont, 12, Font.NORMAL, BaseColor.DARK_GRAY);

        // Định dạng tiêu đề
        Paragraph title = new Paragraph("DANH SÁCH CÁC GIẢNG VIÊN", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Định dạng nội dung
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        // Tiêu đề cho từng cột
        PdfPCell cell1 = new PdfPCell(new Phrase("Mã giảng viên", contentFont));
        PdfPCell cell2 = new PdfPCell(new Phrase("Tên giảng viên", contentFont));
        PdfPCell cell3 = new PdfPCell(new Phrase("Khoa", contentFont));
//        PdfPCell cell4 = new PdfPCell(new Phrase("Địa chỉ", contentFont));

        // Đặt kích thước và định dạng cho cột
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
//        table.addCell(cell4);


        // Thêm dữ liệu từ danh sách các khoa
        for (GiangVien giangVien : GiangVienList) {
            PdfPCell cellMaDv = new PdfPCell(new Phrase(String.valueOf(giangVien.getMaGV()), contentFont));
            PdfPCell cellTenDv = new PdfPCell(new Phrase(giangVien.getTenGV(), contentFont));
            PdfPCell cellDiaChi = new PdfPCell(new Phrase(giangVien.getKhoa().getKhoaName(), contentFont));
//            PdfPCell cellSoDt = new PdfPCell(new Phrase(donvi.getSoDt(), contentFont));


            cellMaDv.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTenDv.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellDiaChi.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cellSoDt.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(cellMaDv);
            table.addCell(cellTenDv);
//            table.addCell(cellSoDt);
            table.addCell(cellDiaChi);

        }
        document.add(new Paragraph("\n"));
        document.add(table);

        document.close();

        byte[] pdfBytes = baos.toByteArray();
        response.setContentLength(pdfBytes.length);
        OutputStream os = response.getOutputStream();
        os.write(pdfBytes);
        os.close();
    }
    @GetMapping("/sinhvien")
    public void displaySinhvienPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf; charset=UTF-8");
        String fontPath = "src/main/resources/times.ttf";
        List<SinhVien> SinhvienList = sinhVienService.getAllSinhVien();

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        // Tạo font và định dạng
        BaseFont customBaseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(customBaseFont, 18, Font.NORMAL, BaseColor.BLACK);
        Font contentFont = new Font(customBaseFont, 12, Font.NORMAL, BaseColor.DARK_GRAY);

        // Định dạng tiêu đề
        Paragraph title = new Paragraph("DANH SÁCH CÁC SINH VIÊN", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Định dạng nội dung
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);

        // Tiêu đề cho từng cột
        PdfPCell cell1 = new PdfPCell(new Phrase("MSSV", contentFont));
        PdfPCell cell2 = new PdfPCell(new Phrase("Tên", contentFont));
        PdfPCell cell3 = new PdfPCell(new Phrase("Giới tính", contentFont));
        PdfPCell cell4 = new PdfPCell(new Phrase("Ngày sinh", contentFont));
        PdfPCell cell5 = new PdfPCell(new Phrase("Quê quán", contentFont));
        PdfPCell cell6 = new PdfPCell(new Phrase("Lớp", contentFont));

        // Đặt kích thước và định dạng cho cột
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell6.setHorizontalAlignment(Element.ALIGN_CENTER);


        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);

        // Thêm dữ liệu từ danh sách các khoa
        for (SinhVien sinhVien : SinhvienList) {
            PdfPCell cellMaDv = new PdfPCell(new Phrase(String.valueOf(sinhVien.getMaSV()), contentFont));
            PdfPCell cellTenDv = new PdfPCell(new Phrase(sinhVien.getTenSV(), contentFont));
            PdfPCell cellDiaChi = new PdfPCell(new Phrase(sinhVien.getGioiTinh(), contentFont));
            PdfPCell cellSoDt = new PdfPCell(new Phrase(String.valueOf(sinhVien.getNgaySinh()), contentFont));
            PdfPCell cellQueQuan = new PdfPCell(new Phrase(sinhVien.getQueQuan(), contentFont));
            PdfPCell cellLop = new PdfPCell(new Phrase(sinhVien.getLop().getTenLop(), contentFont));




            cellMaDv.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTenDv.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellDiaChi.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSoDt.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellQueQuan.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLop.setHorizontalAlignment(Element.ALIGN_CENTER);


            table.addCell(cellMaDv);
            table.addCell(cellTenDv);
            table.addCell(cellDiaChi);
            table.addCell(cellSoDt);
            table.addCell(cellQueQuan);
            table.addCell(cellLop);


        }
        document.add(new Paragraph("\n"));
        document.add(table);

        document.close();

        byte[] pdfBytes = baos.toByteArray();
        response.setContentLength(pdfBytes.length);
        OutputStream os = response.getOutputStream();
        os.write(pdfBytes);
        os.close();
    }
    @GetMapping("/ketquathuctap")
    public void displayKetquaThuctapPdf(
            HttpServletResponse response,
            @RequestParam(name = "keyword", required = false) String keyword
    ) throws IOException, DocumentException {
        response.setContentType("application/pdf; charset=UTF-8");
        String fontPath = "src/main/resources/times.ttf";

        List<KetQuaThucTap> KetQuaThucTapList;

        if (keyword != null && !keyword.isEmpty()) {
            // Filter by keyword (student name or student ID) if the parameter is provided
            KetQuaThucTapList = ketQuaThucTapService.searchKetQuaThucTap(keyword);
        } else {
            // Otherwise, get all results
            KetQuaThucTapList = ketQuaThucTapService.getAllKetQuaThucTap();
        }

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        // Tạo font và định dạng
        BaseFont customBaseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(customBaseFont, 18, Font.NORMAL, BaseColor.BLACK);
        Font contentFont = new Font(customBaseFont, 12, Font.NORMAL, BaseColor.DARK_GRAY);

        // Định dạng tiêu đề
        Paragraph title = new Paragraph("DANH SÁCH KẾT QUẢ CỦA SINH VIÊN", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Định dạng nội dung
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        // Tiêu đề cho từng cột
        PdfPCell cell1 = new PdfPCell(new Phrase("Mã sinh viên", contentFont));
        PdfPCell cell2 = new PdfPCell(new Phrase("Tên sinh viên", contentFont));
        PdfPCell cell3 = new PdfPCell(new Phrase("Lớp", contentFont));
        PdfPCell cell4 = new PdfPCell(new Phrase("Điểm số", contentFont));

        // Đặt kích thước và định dạng cho cột
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);

        // Thêm dữ liệu từ danh sách các khoa
        if (KetQuaThucTapList.isEmpty()) {
            // No data found, add a message
            PdfPCell cellMessage = new PdfPCell(new Phrase("Không có dữ liệu", contentFont));
            cellMessage.setColspan(4); // Span across all columns
            cellMessage.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellMessage);
        } else {
            for (KetQuaThucTap ketqua : KetQuaThucTapList) {
                PdfPCell cellMaDv = new PdfPCell(new Phrase(String.valueOf(ketqua.getSinhVien().getMaSV()), contentFont));
                PdfPCell cellTenDv = new PdfPCell(new Phrase(ketqua.getSinhVien().getTenSV(), contentFont));
                String diemValue = (ketqua.getDiem() != null) ? String.valueOf(ketqua.getDiem()) : "Chưa rõ";
                PdfPCell cellDiaChi = new PdfPCell(new Phrase(diemValue, contentFont));
                PdfPCell cellSoDt = new PdfPCell(new Phrase(ketqua.getSinhVien().getLop().getTenLop(), contentFont));

                cellMaDv.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTenDv.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDiaChi.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSoDt.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cellMaDv);
                table.addCell(cellTenDv);
                table.addCell(cellSoDt);
                table.addCell(cellDiaChi);
            }
        }

        document.add(new Paragraph("\n"));
        document.add(table);

        document.close();

        byte[] pdfBytes = baos.toByteArray();
        response.setContentLength(pdfBytes.length);
        OutputStream os = response.getOutputStream();
        os.write(pdfBytes);
        os.close();
    }

    @GetMapping("/ketquathuctap/{maGv}")
    public void displayKetquaThuctapPdfByGiangVien(
            HttpServletResponse response,
            @RequestParam(name = "keyword", required = false) String keyword,
            @PathVariable Integer maGv
    ) throws IOException, DocumentException {
        response.setContentType("application/pdf; charset=UTF-8");
        String fontPath = "src/main/resources/times.ttf";

        List<KetQuaThucTap> KetQuaThucTapList;

        if (keyword != null && !keyword.isEmpty()) {
            // Filter by keyword (student name or student ID) if the parameter is provided
            KetQuaThucTapList = ketQuaThucTapService.searchKetQuaThucTapByGiangVien(keyword, maGv);
        } else {
            // Otherwise, get all results
            KetQuaThucTapList = ketQuaThucTapService.getKetQuaThucTapByMaGv(maGv,3);
        }

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        // Tạo font và định dạng
        BaseFont customBaseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(customBaseFont, 18, Font.NORMAL, BaseColor.BLACK);
        Font contentFont = new Font(customBaseFont, 12, Font.NORMAL, BaseColor.DARK_GRAY);

        // Định dạng tiêu đề
        Paragraph title = new Paragraph("DANH SÁCH KẾT QUẢ CỦA SINH VIÊN", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Định dạng nội dung
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        // Tiêu đề cho từng cột
        PdfPCell cell1 = new PdfPCell(new Phrase("Mã sinh viên", contentFont));
        PdfPCell cell2 = new PdfPCell(new Phrase("Tên sinh viên", contentFont));
        PdfPCell cell3 = new PdfPCell(new Phrase("Lớp", contentFont));
        PdfPCell cell4 = new PdfPCell(new Phrase("Điểm số", contentFont));

        // Đặt kích thước và định dạng cho cột
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);

        // Thêm dữ liệu từ danh sách các khoa
        if (KetQuaThucTapList.isEmpty()) {
            // No data found, add a message
            PdfPCell cellMessage = new PdfPCell(new Phrase("Không có dữ liệu", contentFont));
            cellMessage.setColspan(4); // Span across all columns
            cellMessage.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellMessage);
        } else {
            for (KetQuaThucTap ketqua : KetQuaThucTapList) {
                PdfPCell cellMaDv = new PdfPCell(new Phrase(String.valueOf(ketqua.getSinhVien().getMaSV()), contentFont));
                PdfPCell cellTenDv = new PdfPCell(new Phrase(ketqua.getSinhVien().getTenSV(), contentFont));
                String diemValue = (ketqua.getDiem() != null) ? String.valueOf(ketqua.getDiem()) : "Chưa rõ";
                PdfPCell cellDiaChi = new PdfPCell(new Phrase(diemValue, contentFont));
                PdfPCell cellSoDt = new PdfPCell(new Phrase(ketqua.getSinhVien().getLop().getTenLop(), contentFont));

                cellMaDv.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTenDv.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDiaChi.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSoDt.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cellMaDv);
                table.addCell(cellTenDv);
                table.addCell(cellSoDt);
                table.addCell(cellDiaChi);
            }
        }

        document.add(new Paragraph("\n"));
        document.add(table);

        document.close();

        byte[] pdfBytes = baos.toByteArray();
        response.setContentLength(pdfBytes.length);
        OutputStream os = response.getOutputStream();
        os.write(pdfBytes);
        os.close();
    }

}
