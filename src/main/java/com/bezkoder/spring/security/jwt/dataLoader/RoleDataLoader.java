package com.bezkoder.spring.security.jwt.dataLoader;

import com.bezkoder.spring.security.jwt.entity.Account;
import com.bezkoder.spring.security.jwt.entity.ERole;
import com.bezkoder.spring.security.jwt.entity.Role;
import com.bezkoder.spring.security.jwt.repository.AccountRepository;
import com.bezkoder.spring.security.jwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Order(1)
public class RoleDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleDataLoader(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder encoder;
    @Override
    public void run(String... args) throws Exception {
        long count = roleRepository.count();
        long count2 = accountRepository.count();
        if(count == 0){
            Role role = new Role(ERole.ROLE_STUDENT);
            Role role1 = new Role(ERole.ROLE_LECTURER);
            Role role2 = new Role(ERole.ROLE_UNIT);
            Role role3 = new Role(ERole.ROLE_CADRE);
            Role role4 = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(role);
            roleRepository.save(role1);
            roleRepository.save(role2);
            roleRepository.save(role3);
            roleRepository.save(role4);
        }
        if(count2 == 0){
            Account account = new Account();
            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            account.setEmail("admin@gmail.com");
            account.setUsername("admin");
            account.setPassword(encoder.encode("admin@123"));
            account.setRoles(roles);
            accountRepository.save(account);
        }
    }
}
