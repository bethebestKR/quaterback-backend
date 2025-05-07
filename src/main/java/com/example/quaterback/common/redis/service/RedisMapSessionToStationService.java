package com.example.quaterback.common.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisMapSessionToStationService {
    private final RedisTemplate<String, String> redisTemplate;

    public String mapSessionToStation(String newSessionId, String stationId) {
        String oldSessionId = redisTemplate.opsForValue().get("stationId:" + stationId);
        if (oldSessionId != null) {
            redisTemplate.delete("sessionId:" + oldSessionId);
            redisTemplate.delete("stationId:" + stationId);
        }

        redisTemplate.opsForValue().set("sessionId:" + newSessionId, stationId);
        redisTemplate.opsForValue().set("stationId:" + stationId, newSessionId);
        return stationId;
    }

    public String getStationId(String sessionId) {
        return redisTemplate.opsForValue().get("sessionId:" + sessionId);
    }

    public void removeMapping(String sessionId) {
        String stationId = getStationId(sessionId);
        if (stationId != null) {
            redisTemplate.delete("stationId:" + stationId);
        }
        redisTemplate.delete("sessionId:" + sessionId);
    }
}
