package com.example.quaterback.login.repository.refresh;

import com.example.quaterback.login.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataRefreshRepository extends JpaRepository<RefreshEntity, Long> {

    Boolean existsByRefresh(String refresh);

    void deleteByRefresh(String refresh);
}
