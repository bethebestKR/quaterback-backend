package com.example.quaterback.api.domain.login.controller;

import com.example.quaterback.api.domain.login.service.JoinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(JoinController.class)
class JoinControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private JoinService joinService;

    JoinControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("join - 회원 가입 성공 시 username을 출력")
    @Test
    void joinSuccess() throws Exception {

        //given
        String username = "userA";
        String password = "tempPw";

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(
                Map.of("username", username, "password", password)
        );
        given(joinService.joinProcess(username, password)).willReturn(username);

        //when & then
        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username));
    }
}