package com.bezkoder.spring.security.jwt.repository;

import java.util.Optional;

import com.bezkoder.spring.security.jwt.entity.Account;
import com.bezkoder.spring.security.jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);

  @Modifying
  int deleteByUser(Account user);
}
