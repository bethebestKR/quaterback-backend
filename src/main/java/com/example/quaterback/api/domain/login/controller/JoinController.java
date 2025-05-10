package com.example.quaterback.api.domain.login.controller;

import com.example.quaterback.api.domain.login.dto.JoinResponse;
import com.example.quaterback.api.domain.login.service.JoinService;
import com.example.quaterback.api.domain.login.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public JoinResponse join(@RequestBody JoinRequest joinRequest) {

        String username = joinService.joinProcess(joinRequest.username(), joinRequest.password());
        return new JoinResponse(username);
    }

}
