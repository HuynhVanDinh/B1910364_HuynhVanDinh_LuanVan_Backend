package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    boolean existsByUsername(String username);
}
