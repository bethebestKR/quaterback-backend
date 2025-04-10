package com.example.quaterback.login.repository.user;

import com.example.quaterback.login.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository("jpaUserRepository")
public class JpaUserRepository implements UserRepository {

    private final SpringDataJpaUserRepository springDataJpaUserRepository;

    public JpaUserRepository(SpringDataJpaUserRepository springDataJpaUserRepository) {
        this.springDataJpaUserRepository = springDataJpaUserRepository;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return springDataJpaUserRepository.existsByUsername(username);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return springDataJpaUserRepository.findByUsername(username);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return springDataJpaUserRepository.save(userEntity);
    }
}
