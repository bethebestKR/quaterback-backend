package com.example.quaterback.login.service;

import com.example.quaterback.login.entity.RefreshEntity;
import com.example.quaterback.login.jwt.JWTUtil;
import com.example.quaterback.login.repository.RefreshRepository;
import com.example.quaterback.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class ReissueService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public String validateRefresh(Cookie[] cookies){
        // refresh 토큰 추출
        String refreshToken = CookieUtil.extractRefreshToken(cookies);

        // null, 유효성 (만료 여부 등), refreshToken인지 확인
        if (!jwtUtil.isValidateRefreshToken(refreshToken)){

            throw new RuntimeException("invalid token");
        }

        // DB에 저장되어 있는지 확인. 추후에 db말고 redis로 변경 가능
        Boolean isExist = refreshRepository.existsByRefresh(refreshToken);
        if (!isExist) {

            throw new RuntimeException("invalid token");
        }

        return refreshToken;
    }

    public String getNewAccessToken(String refreshToken){
        String username = jwtUtil.getUsername(refreshToken);
        return jwtUtil.createJwt("accessToken", username, 600000L);
    }

    @Transactional
    public String getNewRefreshToken(String refreshToken){
        String username = jwtUtil.getUsername(refreshToken);
        String newRefresh = jwtUtil.createJwt("refreshToken", username, 86400000L);
        refreshRepository.deleteByRefresh(refreshToken);
        addRefreshEntity(username, newRefresh, 86400000L);
        return newRefresh;
    }

    public void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = RefreshEntity.builder()
                .username(username)
                .refresh(refresh)
                .expiration(date.toString())
                .build();
        refreshRepository.save(refreshEntity);
    }

}
