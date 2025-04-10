package com.example.quaterback.login.repository.refresh;

import com.example.quaterback.login.entity.RefreshEntity;
import org.springframework.stereotype.Repository;

@Repository("jpaRefreshRepository")
public class JpaRefreshRepository implements RefreshRepository {

    private final SpringDataJpaRefreshRepository springDataJpaRefreshRepository;

    public JpaRefreshRepository(SpringDataJpaRefreshRepository springDataJpaRefreshRepository) {
        this.springDataJpaRefreshRepository = springDataJpaRefreshRepository;
    }


    @Override
    public Boolean existsByRefresh(String refresh) {
        return springDataJpaRefreshRepository.existsByRefresh(refresh);
    }

    @Override
    public void deleteByRefresh(String refresh) {
        springDataJpaRefreshRepository.deleteByRefresh(refresh);
    }

    @Override
    public RefreshEntity save(RefreshEntity refreshEntity) {
        return springDataJpaRefreshRepository.save(refreshEntity);
    }
}
