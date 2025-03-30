package com.refathasan.school_management.services.jwt;

import com.refathasan.school_management.entities.User;
import com.refathasan.school_management.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //TODO: Write User Details logic from Database
        Optional<User> userOptional  = userRepo.findFirstByEmail(email);
        if(userOptional.isEmpty()) throw new UsernameNotFoundException("Username not found",null);
            return new org.springframework.security.core.userdetails.User(userOptional.get().getEmail(),userOptional.get().getPassword(),new ArrayList<>());

    }
}
