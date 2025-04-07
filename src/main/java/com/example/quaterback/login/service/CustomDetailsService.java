package com.example.quaterback.login.service;

import com.example.quaterback.login.dto.CustomUserDetails;
import com.example.quaterback.login.entity.UserEntity;
import com.example.quaterback.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CustomDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null){

            return new CustomUserDetails(userEntity);
        }
        throw new UsernameNotFoundException("username not found");
    }
}
