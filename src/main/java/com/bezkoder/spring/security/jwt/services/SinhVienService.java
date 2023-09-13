package com.bezkoder.spring.security.jwt.services;

import com.bezkoder.spring.security.jwt.entity.*;
import com.bezkoder.spring.security.jwt.payload.request.SinhVienDto;
import com.bezkoder.spring.security.jwt.repository.AccountRepository;
import com.bezkoder.spring.security.jwt.repository.LopRepository;
import com.bezkoder.spring.security.jwt.repository.RoleRepository;
import com.bezkoder.spring.security.jwt.repository.SinhVienRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.Normalizer;
import java.util.Properties;

import java.util.List;
import java.util.Set;

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
    public List<SinhVien> getAllSinhVien() {
        return sinhVienRepository.findAll();
    }

    public SinhVien getSinhVienById(Integer id) {
        return sinhVienRepository.findById(id).orElse(null);
    }
    @Transactional
    public SinhVien createSinhVien(SinhVienDto sinhVienDto, Integer lopId, String email) {
        Lop lop = lopRepository.findById(lopId).orElse(null);

        if (lop == null) {
            return null;

        } else {
            SinhVien sinhVien = new SinhVien(
                    sinhVienDto.getTenSV(),
                    sinhVienDto.getGioiTinh(),
                    sinhVienDto.getNgaySinh(),
                    sinhVienDto.getQueQuan(),
                    lop
            );

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
    public SinhVien updateSinhVien(Integer sinhvienId, SinhVienDto sinhVienDto, Integer lopId) {
        SinhVien existingSinhVien = getSinhVienById(sinhvienId);
        Lop lop = lopRepository.findById(lopId).orElse(null);
        if (lop != null) {
            return null;

        } else {
            existingSinhVien.setTenSV(sinhVienDto.getTenSV());
            existingSinhVien.setGioiTinh(sinhVienDto.getGioiTinh());
            existingSinhVien.setNgaySinh(sinhVienDto.getNgaySinh());
            existingSinhVien.setQueQuan(sinhVienDto.getQueQuan());
            existingSinhVien.setLop(lop);

            return sinhVienRepository.save(existingSinhVien);
        }

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
