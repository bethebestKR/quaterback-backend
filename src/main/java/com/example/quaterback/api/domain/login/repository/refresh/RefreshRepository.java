package com.example.quaterback.api.domain.login.repository.refresh;

import com.example.quaterback.api.domain.login.entity.RefreshEntity;

public interface RefreshRepository {
    Boolean existsByRefresh(String refresh);

    void deleteByRefresh(String refresh);

    RefreshEntity save(RefreshEntity refreshEntity);
}
