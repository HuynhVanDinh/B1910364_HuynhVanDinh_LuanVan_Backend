package com.bezkoder.spring.security.jwt.repository;

import java.util.Optional;

import com.bezkoder.spring.security.jwt.entity.ERole;
import com.bezkoder.spring.security.jwt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
