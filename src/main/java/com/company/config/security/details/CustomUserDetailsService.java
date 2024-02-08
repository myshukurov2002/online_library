package com.company.config.security.details;

import com.company.expections.exp.AppBadRequestException;
import com.company.user.entities.UserEntity;
import com.company.user.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository
                .findByIdAndVisibilityTrue(UUID.fromString(username))
                .orElseThrow(() -> new AppBadRequestException("login.or.password.wrong"));

        return new CustomUserDetails(user);
    }
}
