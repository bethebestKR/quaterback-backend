package com.example.quaterback.login.jwt;

import com.example.quaterback.login.dto.CustomUserDetails;
import com.example.quaterback.login.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 Authorization에 담긴 토큰을 꺼냄
        String authorization = request.getHeader("Authorization");
        // 토큰이 없다면 다음 필터로 넘김
        if (!jwtUtil.hasAuthorizationToken(authorization)){
            filterChain.doFilter(request, response);

            return;
        }

        String accessToken = authorization.substring(7);

        // 토큰 유효 여부 확인, 유효하지 않을 시 다음 필터로 넘기지 않음
        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        if (!jwtUtil.isValidateAccessToken(accessToken)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        // username 값을 획득
        String username = jwtUtil.getUsername(accessToken);

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
