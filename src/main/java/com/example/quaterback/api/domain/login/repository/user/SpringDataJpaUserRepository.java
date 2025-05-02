package com.example.quaterback.api.domain.login.repository.user;

import com.example.quaterback.api.domain.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaUserRepository extends JpaRepository<UserEntity, Integer> {
    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);

    UserEntity save(UserEntity userEntity);
}
