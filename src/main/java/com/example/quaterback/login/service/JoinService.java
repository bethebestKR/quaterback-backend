package com.example.quaterback.login.service;

import com.example.quaterback.exception.DuplicateJoinException;
import com.example.quaterback.login.domain.UserDomain;
import com.example.quaterback.login.entity.UserEntity;
import com.example.quaterback.login.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(@Qualifier("jpaUserRepository") UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public String joinProcess(String username, String password) {

        if (userRepository.existsByUsername(username)) {
            throw new DuplicateJoinException("이미 존재하는 ID입니다.");
        }

        UserDomain userDomain = UserDomain.createUserDomain(username, bCryptPasswordEncoder.encode(password));

        UserEntity userEntity = UserEntity.from(userDomain);
        UserDomain returnUserDomain = userRepository.save(userEntity).toDomain();
        String returnUsername = returnUserDomain.getUsername();
        return returnUsername;

    }
}
