package com.example.quaterback.api.domain.login.service;

import com.example.quaterback.api.domain.login.dto.CustomUserDetails;
import com.example.quaterback.api.domain.login.entity.UserEntity;
import com.example.quaterback.api.domain.login.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomDetailsService(@Qualifier("jpaUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {

            return new CustomUserDetails(userEntity);
        }
        throw new UsernameNotFoundException("username not found");
    }
}
