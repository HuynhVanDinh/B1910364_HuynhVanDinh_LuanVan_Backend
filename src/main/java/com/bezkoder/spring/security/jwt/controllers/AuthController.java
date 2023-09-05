package com.bezkoder.spring.security.jwt.controllers;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bezkoder.spring.security.jwt.entity.Account;
import com.bezkoder.spring.security.jwt.entity.ERole;
import com.bezkoder.spring.security.jwt.entity.RefreshToken;
import com.bezkoder.spring.security.jwt.entity.Role;
import com.bezkoder.spring.security.jwt.exception.TokenRefreshException;
import com.bezkoder.spring.security.jwt.payload.request.LoginDto;
import com.bezkoder.spring.security.jwt.payload.request.SignupDto;
import com.bezkoder.spring.security.jwt.payload.request.TokenRefreshDto;
import com.bezkoder.spring.security.jwt.payload.response.JwtResponse;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.payload.response.TokenRefreshResponse;
import com.bezkoder.spring.security.jwt.repository.RoleRepository;
import com.bezkoder.spring.security.jwt.repository.UserRepository;
import com.bezkoder.spring.security.jwt.security.jwt.JwtUtils;
import com.bezkoder.spring.security.jwt.security.services.RefreshTokenService;
import com.bezkoder.spring.security.jwt.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomStringUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  RefreshTokenService refreshTokenService;


  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        .collect(Collectors.toList());

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
        userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDto signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    Account user = new Account(signUpRequest.getUsername(), signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "lecturer":
          Role lectureRole = roleRepository.findByName(ERole.ROLE_LECTURER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(lectureRole);

          break;
          case "cadre":
            Role cadreRole = roleRepository.findByName(ERole.ROLE_CADRE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(cadreRole);

            break;
          case "unit":
            Role unitRole = roleRepository.findByName(ERole.ROLE_UNIT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(unitRole);

            break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found..."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshDto request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        })
        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
            "Refresh token is not in database!"));
  }
  
  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getId();
    refreshTokenService.deleteByUserId(userId);
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }
  @PostMapping("/forgotpassword")
  public ResponseEntity<?> forgotPassword(@RequestParam String email) {
    Optional<Account> optionalAccount = userRepository.findByEmail(email);

    if (optionalAccount.isPresent()) {
      Account account = optionalAccount.get();
      String newPassword = generateRandomPassword();
      account.setPassword(encoder.encode(newPassword));
      userRepository.save(account);

      // Gửi email
      sendPasswordResetEmail(account.getEmail(), newPassword);

      return ResponseEntity.ok(new MessageResponse("Mật khẩu mới đã được gửi đến email của bạn."));
    } else {
      return ResponseEntity.badRequest().body(new MessageResponse("Email không tồn tại trong hệ thống."));
    }
  }

  private void sendPasswordResetEmail(String toEmail, String newPassword) {
    // Cấu hình properties cho JavaMail
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com"); // Thay thế bằng SMTP server thực tế
    props.put("mail.smtp.port", "587"); // Thay thế bằng cổng SMTP thực tế

    // Tạo một phiên làm việc JavaMail
    Session session = Session.getInstance(props, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("dinhb1910364@student.ctu.edu.vn", "@uFuFKBU8R");
      }
    });

    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("dinhb1910364@student.ctu.edu.vn")); // Địa chỉ email người gửi
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Địa chỉ email người nhận
      message.setSubject("Mật khẩu mới"); // Tiêu đề email
      message.setText("Mật khẩu mới của bạn là: " + newPassword); // Nội dung email

      // Gửi email
      Transport.send(message);

      System.out.println("Sent password reset email to " + toEmail);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
  private String generateRandomPassword() {
    // Đoạn này tạo mật khẩu ngẫu nhiên, ví dụ sử dụng thư viện Apache Commons Lang
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    int length = 10; // Độ dài mật khẩu
    return RandomStringUtils.random(length, characters);
  }
}
