package com.example.quaterback.login.repository.refresh;

import com.example.quaterback.login.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("jpaRefreshRepository")
public interface JpaRefreshRepository extends RefreshRepository, JpaRepository<RefreshEntity, Long> {
    Boolean existsByRefresh(String refresh);
    void deleteByRefresh(String refresh);
    RefreshEntity save(RefreshEntity refreshEntity);
}
