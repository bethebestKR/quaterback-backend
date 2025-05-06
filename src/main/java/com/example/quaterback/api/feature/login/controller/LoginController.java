package com.example.quaterback.api.feature.login.controller;

import com.example.quaterback.api.feature.login.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {
    @Operation(summary = "로그인", description = "로그인을 위한 테스트용 Swagger 엔드포인트입니다. 실제 처리는 필터에서 수행됩니다.")
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
    }
}
