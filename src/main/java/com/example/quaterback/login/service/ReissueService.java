package com.example.quaterback.login.service;

import com.example.quaterback.login.constant.RefreshTokenErrorType;
import com.example.quaterback.login.entity.RefreshEntity;
import com.example.quaterback.login.jwt.JWTUtil;
import com.example.quaterback.login.repository.RefreshRepository;
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
        String refreshToken = extractRefreshToken(cookies);
        // null 확인
        if (refreshToken == null) {
            return RefreshTokenErrorType.NULL.toString();
        }
        // 유효성 확인 (만료 여부 등)
        if (!jwtUtil.isValidate(refreshToken)){
            return RefreshTokenErrorType.EXPIRED.toString();
        }
        // refreshToken인지 확인
        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refreshToken")){
            return RefreshTokenErrorType.NOT.toString();
        }
        // DB에 저장되어 있는지 확인. 추후에 db말고 redis로 변경 가능
        Boolean isExist = refreshRepository.existsByRefresh(refreshToken);
        if (!isExist) {
            return RefreshTokenErrorType.INVALID.toString();
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

    private String extractRefreshToken(Cookie[] cookies) {

        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refreshToken")) {

                return cookie.getValue();
            }
        }
        return null;
    }

    public void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

}
