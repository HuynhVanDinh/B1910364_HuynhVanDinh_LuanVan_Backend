package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.SinhVienDto;
import com.bezkoder.spring.security.jwt.repository.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Properties;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private KetQuaThucTapRepository ketQuaThucTapRepository;

    @Autowired
    private ThoiGianDangKyRepository thoiGianDangKyRepository;
    private final EmailService emailService;

    @Autowired
    public SinhVienService(EmailService emailService) {
        this.emailService = emailService;
    }
    public List<SinhVien> getAllSinhVien() {
        return sinhVienRepository.findAll();
    }

    public SinhVien getSinhVienById(Integer id) {
        return sinhVienRepository.findById(id).orElse(null);
    }

    public List<SinhVien> getSinhVienByLop(Integer maLop) {
        Lop lop = lopRepository.findById(maLop).orElse(null);
        return sinhVienRepository.findSinhVienByLop(lop);
    }
    public SinhVien getSinhVienByAccountId(Integer accountid) {
        return sinhVienRepository.findByAccountId(accountid).orElse(null);
    }
    public List<SinhVien> getSinhVienChuaDangKy() {
        List<SinhVien> allSinhVien = sinhVienRepository.findAll();
        List<Integer> sinhVienIdsWithKetQuaThucTap = ketQuaThucTapRepository.findAll().stream()
                .map(ketQuaThucTap -> ketQuaThucTap.getSinhVien().getMaSV())
                .collect(Collectors.toList());

        return allSinhVien.stream()
                .filter(sinhVien -> !sinhVienIdsWithKetQuaThucTap.contains(sinhVien.getMaSV()))
                .collect(Collectors.toList());
    }

    public void sendEmailToChuaDangKyStudents() {
        List<SinhVien> chuaDangKyStudents = getSinhVienChuaDangKy();

        for (SinhVien sinhVien : chuaDangKyStudents) {
            ThoiGianDangKy thoiGianDangKy = thoiGianDangKyRepository.findByKhoa(sinhVien.getLop().getKhoa());

            String studentEmail = sinhVien.getAccount().getEmail();


            String subject = "Về việc Thực tập thực tế (TTTT).";
            String content = "Chào các em,\n"+"Các em cố  gắng tìm công ty TTTT vì sau thời gian "+thoiGianDangKy.getTgkt()+" em nào chưa tìm được công ty TTTT thì thường Khoa sẽ phân";

            emailService.sendEmail(studentEmail, subject, content);
        }
    }

    @Transactional
    public SinhVien createSinhVien(SinhVienDto sinhVienDto, Integer lopId, String email) {
        Lop lop = lopRepository.findById(lopId).orElse(null);

        if (lop == null) {
            return null;

        } else {
            SinhVien sinhVien = new SinhVien(
                    sinhVienDto.getTenSV(),
                    sinhVienDto.getHinhAnh(),
                    sinhVienDto.getGioiTinh(),
                    sinhVienDto.getNgaySinh(),
                    sinhVienDto.getQueQuan(),
                    lop
            );
//            sinhVien.setHinhAnh(this.getCurrentDateTime()+"user.png");

            SinhVien savedSinhVien = sinhVienRepository.save(sinhVien);
            String tenSV = savedSinhVien.getTenSV().replaceAll(" ", "").toLowerCase();
            tenSV = Normalizer.normalize(tenSV, Normalizer.Form.NFD);
            tenSV = tenSV.replaceAll("[^\\p{ASCII}]", "");

            // Kiểm tra xem username đã tồn tại trong tài khoản chưa
            int count = 1;
            String newTenSV = tenSV;
            while (accountRepository.existsByUsername(newTenSV)) {
                newTenSV = tenSV + count;
                count++;
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String randomPassword = RandomStringUtils.randomAlphanumeric(10);
            String encodedPassword = passwordEncoder.encode(randomPassword);
            Account taiKhoan = new Account();
            taiKhoan.setUsername(newTenSV);
            taiKhoan.setPassword(encodedPassword);
            taiKhoan.setEmail(email);
            Role unitRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            taiKhoan.setRoles(Set.of(unitRole));

//            accountRepository.save(taiKhoan);
            sinhVien.setAccount(taiKhoan);
            sinhVienRepository.save(sinhVien);
            // Gửi email
            sendEmail(email, newTenSV, randomPassword);

            return savedSinhVien;

        }

    }
    @Transactional
    public SinhVien createSinhVienFromExcel(MultipartFile excelFile) {
        try (InputStream inputStream = excelFile.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                // Assuming your Excel has columns for tenSV, hinhAnh, gioiTinh, ngaySinh, queQuan, lopId, email

                String tenSV = cellIterator.next().getStringCellValue();
                String hinhAnh = cellIterator.next().getStringCellValue();
                String gioiTinh = cellIterator.next().getStringCellValue();
                String ngaySinhStr = cellIterator.next().getStringCellValue();
                LocalDate ngaySinh = LocalDate.parse(ngaySinhStr);
                String queQuan = cellIterator.next().getStringCellValue();
                Integer lopId = (int) cellIterator.next().getNumericCellValue(); // Assuming lopId is numeric
                String email = cellIterator.next().getStringCellValue();

                Lop lop = lopRepository.findById(lopId).orElse(null);

                if (lop != null) {
                    SinhVien sinhVien = new SinhVien(tenSV, hinhAnh, gioiTinh, ngaySinh, queQuan, lop);

                    SinhVien savedSinhVien = sinhVienRepository.save(sinhVien);
                    String username = generateUniqueUsername(savedSinhVien.getTenSV());

                    // Create an account for the student
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String randomPassword = RandomStringUtils.randomAlphanumeric(10);
                    String encodedPassword = passwordEncoder.encode(randomPassword);

                    Account taiKhoan = new Account();
                    taiKhoan.setUsername(username);
                    taiKhoan.setPassword(encodedPassword);
                    taiKhoan.setEmail(email);

                    // Set roles for the account, assuming ERole is an Enum representing roles
                    Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    taiKhoan.setRoles(Set.of(studentRole));

                    accountRepository.save(taiKhoan);

                    // Set the account for the SinhVien
                    sinhVien.setAccount(taiKhoan);
                    sinhVienRepository.save(sinhVien);

                    // Send email
                    sendEmail(email, username, randomPassword);
                }
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }

        return null; // You might want to change the return type or behavior based on your needs
    }

    private String generateUniqueUsername(String baseUsername) {
        String username = baseUsername.replaceAll(" ", "").toLowerCase();
        username = Normalizer.normalize(username, Normalizer.Form.NFD);
        username = username.replaceAll("[^\\p{ASCII}]", "");

        // Check if username already exists, similar to your existing logic
        int count = 1;
        String newUsername = username;
        while (accountRepository.existsByUsername(newUsername)) {
            newUsername = username + count;
            count++;
        }

        return newUsername;
    }

    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }
    @Transactional
    public SinhVien updateSinhVien(Integer sinhvienId, SinhVienDto sinhVienDto, Integer lopId, String email) {
        SinhVien existingSinhVien = getSinhVienById(sinhvienId);
        Lop lop = lopRepository.findById(lopId).orElse(null);
        if (lop == null ) {
            return null;

        } else {
            existingSinhVien.setTenSV(sinhVienDto.getTenSV());
//            existingSinhVien.setHinhAnh("user.png");
            existingSinhVien.setGioiTinh(sinhVienDto.getGioiTinh());
            existingSinhVien.setNgaySinh(sinhVienDto.getNgaySinh());
            existingSinhVien.setQueQuan(sinhVienDto.getQueQuan());
            existingSinhVien.setLop(lop);
            // Cập nhật thông tin của Account
            Account account = existingSinhVien.getAccount();
            account.setEmail(email);

            return sinhVienRepository.save(existingSinhVien);
        }

    }

    @Transactional
    public SinhVien editSinhVien(Integer sinhvienId, SinhVienDto sinhVienDto) {
        SinhVien existingSinhVien = getSinhVienById(sinhvienId);
            existingSinhVien.setTenSV(sinhVienDto.getTenSV());
            existingSinhVien.setNgaySinh(sinhVienDto.getNgaySinh());
            existingSinhVien.setQueQuan(sinhVienDto.getQueQuan());
            return sinhVienRepository.save(existingSinhVien);

    }
    @Transactional
    public SinhVien updateAvt(Integer sinhvienId, String hinhAnh) {
        SinhVien existingSinhVien = getSinhVienById(sinhvienId);
            existingSinhVien.setHinhAnh(hinhAnh);
            return sinhVienRepository.save(existingSinhVien);
    }

    @Transactional
    public void deleteSinhVien(Integer sinhvienId) {
        SinhVien existingSinhVien = getSinhVienById(sinhvienId);

        sinhVienRepository.delete(existingSinhVien);
    }
    @Transactional
    public List<SinhVien> searchSinhVienByName(String tenSV) {
        // Gọi phương thức tìm kiếm trong repository
        return sinhVienRepository.findByTenSVContainingIgnoreCase(tenSV);
    }


    private void sendEmail(String to, String username, String password) {
        String from = "dinhb1910364@student.ctu.edu.vn"; // Địa chỉ email nguồn
        String host = "smtp.gmail.com"; // SMTP server của Gmail
        final String usernameEmail = "dinhb1910364@student.ctu.edu.vn";
        final String passwordEmail = "@uFuFKBU8R";

        // Cấu hình thông tin đăng nhập và gửi email
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        // Tạo đối tượng Session
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usernameEmail, passwordEmail);
            }
        });

        try {
            // Tạo đối tượng MimeMessage
            MimeMessage message = new MimeMessage(session);

            // Đặt thông tin người gửi và người nhận
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Đặt tiêu đề và nội dung email
            message.setSubject("Thông tin tài khoản");
            String content = "Chào bạn,\n\n";
            content += "Dưới đây là thông tin tài khoản của bạn:\n\n";
            content += "Tên đăng nhập: " + username + "\n";
            content += "Mật khẩu: " + password + "\n\n";
            content += "Hãy thay đổi mật khẩu sau khi đăng nhập.\n\n";
            content += "Trân trọng,\n";
            content += "Ban quản trị";
            message.setText(content);

            // Gửi email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
