package com.refathasan.school_management.services.admin;

import com.refathasan.school_management.entities.User;
import com.refathasan.school_management.enums.UserRole;
import com.refathasan.school_management.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl {
    private final UserRepo userRepo;
    public AdminServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @PostConstruct
    public void createAdmin() {
        User adminAccount = userRepo.findByRole(UserRole.ADMIN);
        if(adminAccount==null){
            User adminUser = new User();
            adminUser.setEmail("admin@test.com");
            adminUser.setFirst_name("Admin");
            adminUser.setRole(UserRole.ADMIN);
            adminUser.setLast_name("Test");
            adminUser.setPassword(new BCryptPasswordEncoder().encode("password"));
            userRepo.save(adminUser);
        }

    }
}
