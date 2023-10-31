package com.bezkoder.spring.security.jwt.dataLoader;

import com.bezkoder.spring.security.jwt.entity.ERole;
import com.bezkoder.spring.security.jwt.entity.Role;
import com.bezkoder.spring.security.jwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RoleDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleDataLoader(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = roleRepository.count();
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
    }
}
