package com.example.quaterback.websocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RefreshTimeoutServiceIntegrationTest {

    @Autowired
    private RefreshTimeoutService refreshTimeoutService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @BeforeEach
    void setup() {
        redisTemplate.opsForValue().set("sessionId:session123", "stationABC");
        redisTemplate.opsForValue().set("stationId:stationABC", "session123");
    }

    @Test
    void testRefreshTimeoutActuallyUpdatesRedis() {

        // when
        refreshTimeoutService.refreshTimeout("session123");
        System.out.println(redisTemplate.opsForValue().get("heartbeat:stationABC"));
        // then
        refreshTimeoutService.refreshTimeout("session123");
        System.out.println(redisTemplate.opsForValue().get("heartbeat:stationABC"));

        String result = redisTemplate.opsForValue().get("heartbeat:stationABC");
        assertThat(result).isNotNull();
    }
}
