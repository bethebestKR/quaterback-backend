package com.example.quaterback.login.repository.refresh;

import com.example.quaterback.login.entity.RefreshEntity;

public interface RefreshRepository {
    Boolean existsByRefresh(String refresh);
    void deleteByRefresh(String refresh);
    RefreshEntity save(RefreshEntity refreshEntity);
}
