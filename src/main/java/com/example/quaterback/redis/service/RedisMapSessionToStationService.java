package com.example.quaterback.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class RedisMapSessionToStationService {
    private final RedisTemplate<String, String> redisTemplate;

    public void mapSessionToStation(String sessionId, String stationId) {
        redisTemplate.opsForValue().set("session:" + sessionId, stationId);
    }

    public String getStationId(String sessionId) {
        return redisTemplate.opsForValue().get("session:" + sessionId);
    }

    public void removeMapping(String sessionId) {
        redisTemplate.delete("session:" + sessionId);
    }
}
