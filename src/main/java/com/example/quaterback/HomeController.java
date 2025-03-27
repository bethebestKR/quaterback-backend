package com.example.quaterback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    private JdbcTemplate jdbcTemplate; // JdbcTemplate 사용

    @Autowired
    private RedisTemplate<String, String> redisTemplate; // RedisTemplate 사용

    @GetMapping("/")
    public String index() {
        // MySQL 연결 확인
        try {
            jdbcTemplate.execute("SELECT 1"); // MySQL 연결을 확인하기 위한 간단한 쿼리
            return "Successfully connected to MySQL!";
        } catch (Exception e) {
            return "Failed to connect to MySQL: " + e.getMessage();
        }
    }



    @GetMapping("/check-redis")
    public String checkRedisConnection() {
        try {
            // Redis에 간단한 PING 명령 실행하여 연결 확인
            String response = redisTemplate.getConnectionFactory().getConnection().ping();
            if ("PONG".equals(response)) {
                return "Successfully connected to Redis!";
            } else {
                return "Failed to connect to Redis!";
            }
        } catch (Exception e) {
            return "Failed to connect to Redis: " + e.getMessage();
        }
    }
}
