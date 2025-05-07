package com.example.quaterback.api.domain.login.repository.user;

import com.example.quaterback.api.domain.login.domain.UserDomain;
import com.example.quaterback.api.domain.login.entity.UserEntity;

public interface UserRepository {
    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);

    UserDomain save(UserDomain userDomain);
}
