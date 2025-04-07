package com.example.quaterback.login.controller;

import com.example.quaterback.login.constant.RefreshTokenErrorType;
import com.example.quaterback.login.service.ReissueService;
import com.example.quaterback.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequiredArgsConstructor
@RestController
public class ReissueController {

    private final ReissueService reissueService;

    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = reissueService.validateRefresh(request.getCookies());

        if(Arrays.stream(RefreshTokenErrorType.values())
                .anyMatch(type -> type.toString().equals(refreshToken))) {

            return new ResponseEntity<>(refreshToken, HttpStatus.BAD_REQUEST);
        }

        String newRefreshToken = reissueService.getNewRefreshToken(refreshToken);
        String newAccessToken = reissueService.getNewAccessToken(refreshToken);
        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.addCookie(CookieUtil.createCookie("refreshToken", newRefreshToken));
        return new ResponseEntity<>("reissued refresh token", HttpStatus.OK);
    }

}
