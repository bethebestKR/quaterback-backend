package com.example.quaterback.api.domain.login.jwt;

import com.example.quaterback.api.domain.login.service.ReissueService;
import com.example.quaterback.api.feature.login.dto.LoginRequest;
import com.example.quaterback.api.feature.login.dto.LoginResponse;
import com.example.quaterback.common.util.CookieUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ReissueService reissueService;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, ReissueService reissueService) {
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.reissueService = reissueService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);

            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("Faild to parse login request", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String username = authResult.getName();

        String accessToken = jwtUtil.createJwt("accessToken", username, 600000L);
        String refreshToken = jwtUtil.createJwt("refreshToken", username, 86400000L);

        reissueService.addRefreshEntity(username, refreshToken, 86400000L);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(CookieUtil.createCookie("refreshToken", refreshToken));
        response.setStatus(HttpStatus.OK.value());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        LoginResponse loginResponse = new LoginResponse(username);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(loginResponse);
        response.getWriter().write(json);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(401);
    }

}
