package com.refathasan.school_management.repo;

import com.refathasan.school_management.entities.User;
import com.refathasan.school_management.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    User findByRole(UserRole userRole);
}
