package com.example.user_service;

import com.example.user_service.entity.UserEntity;
import com.example.user_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> byEmail = userRepository.findByEmail(username);
        if (byEmail.isEmpty())
            throw new UsernameNotFoundException(username);
        return new MyUserDetails(byEmail.get());
    }
}
