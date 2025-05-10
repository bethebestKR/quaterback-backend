package com.example.quaterback.common.redis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class RedisMapSessionToStationServiceTest {

    private RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> valueOps;
    private RedisMapSessionToStationService service;

    @BeforeEach
    void setUp() {
        redisTemplate = mock(RedisTemplate.class);
        valueOps = mock(ValueOperations.class);
        given(redisTemplate.opsForValue()).willReturn(valueOps);

        service = new RedisMapSessionToStationService(redisTemplate);
    }

    @Test
    void mapSessionToStation_sessionId와_stationId를_양방향_매핑한다() {
        // given
        String sessionId = "session123";
        String stationId = "stationABC";

        given(valueOps.get("stationId:" + stationId)).willReturn(null);

        // when
        String result = service.mapSessionToStation(sessionId, stationId);

        // then
        assertThat(result).isEqualTo(stationId);
        then(valueOps).should().set("sessionId:" + sessionId, stationId);
        then(valueOps).should().set("stationId:" + stationId, sessionId);
    }

    @Test
    void mapSessionToStation_이미_존재하면_삭제하고_새로운_매핑_저장() {
        // given
        String oldSessionId = "oldSession";
        String newSessionId = "newSession";
        String stationId = "station1";

        given(valueOps.get("stationId:" + stationId)).willReturn(oldSessionId);

        // when
        service.mapSessionToStation(newSessionId, stationId);

        // then
        then(redisTemplate).should().delete("sessionId:" + oldSessionId);
        then(redisTemplate).should().delete("stationId:" + stationId);
        then(valueOps).should().set("sessionId:" + newSessionId, stationId);
        then(valueOps).should().set("stationId:" + stationId, newSessionId);
    }

    @Test
    void getStationId_sessionId로_staionId얻기() {
        // given
        String sessionId = "sessionX";
        given(valueOps.get("sessionId:" + sessionId)).willReturn("stationY");

        // when
        String result = service.getStationId(sessionId);

        // then
        assertThat(result).isEqualTo("stationY");
    }

    @Test
    void removeMapping_세션종료하면_관련_매핑_모두_삭제() {
        // given
        String sessionId = "session1";
        String stationId = "station9";

        given(valueOps.get("sessionId:" + sessionId)).willReturn(stationId);

        // when
        service.removeMapping(sessionId);

        // then
        then(redisTemplate).should().delete("stationId:" + stationId);
        then(redisTemplate).should().delete("sessionId:" + sessionId);
    }

}