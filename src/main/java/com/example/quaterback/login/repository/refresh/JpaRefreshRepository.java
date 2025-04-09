package com.example.quaterback.login.repository.refresh;

import com.example.quaterback.login.entity.RefreshEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository("jpaRefreshRepository")
public class JpaRefreshRepository implements RefreshRepository{

    private final SpringDataRefreshRepository springDataRefreshRepository;

    @Override
    public Boolean existsByRefresh(String refresh) {
        return springDataRefreshRepository.existsByRefresh(refresh);
    }

    @Override
    public void deleteByRefresh(String refresh) {
        springDataRefreshRepository.deleteByRefresh(refresh);
    }

    @Override
    public void save(RefreshEntity refreshEntity) {
        springDataRefreshRepository.save(refreshEntity);
    }
}
