package com.example.quaterback.login.repository.user;

import com.example.quaterback.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("jpaUserRepository")
public interface JpaUserRepository extends UserRepository, JpaRepository<UserEntity, Integer> {
    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
    UserEntity save(UserEntity userEntity);
}
