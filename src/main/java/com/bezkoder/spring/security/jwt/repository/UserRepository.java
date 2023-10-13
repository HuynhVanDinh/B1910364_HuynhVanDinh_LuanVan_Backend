package com.bezkoder.spring.security.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.security.jwt.entity.Account;

@Repository
public interface UserRepository extends JpaRepository<Account, Integer> {
  Optional<Account> findByUsername(String username);
  Optional<Account> findByEmail(String email);
  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
