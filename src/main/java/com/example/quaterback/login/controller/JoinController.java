package com.example.quaterback.login.controller;

import com.example.quaterback.login.dto.JoinRequest;
import com.example.quaterback.login.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String join(JoinRequest joinRequest){

        String username = joinService.joinProcess(joinRequest.username(), joinRequest.password());
        return username;
    }

}
