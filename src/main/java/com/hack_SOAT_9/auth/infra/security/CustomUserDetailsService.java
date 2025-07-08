package com.hack_SOAT_9.auth.infra.security;

import com.hack_SOAT_9.auth.entity.RegisterEntity;
import com.hack_SOAT_9.auth.repository.RegisterRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private RegisterRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        RegisterEntity user = this.repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not Found"));
        return new User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
