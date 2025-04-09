package com.example.quaterback.login.repository.user;

import com.example.quaterback.login.entity.UserEntity;

public interface UserRepository {
    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);

    UserEntity save(UserEntity userEntity);
}
