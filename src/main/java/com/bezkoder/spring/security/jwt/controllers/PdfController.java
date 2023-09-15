package com.bezkoder.spring.security.jwt.controllers;
import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.services.KhoaService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
