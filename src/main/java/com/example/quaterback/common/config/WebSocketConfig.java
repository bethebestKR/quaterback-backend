package com.example.quaterback.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/*
 1. WebSocketConfigurer : 웹소켓을 구현하겠다. 반드시 상속받고 시작
 2. registerWebSocketHandlers() => /ocpp/{stationId}: WebSocket 접속 주소
                                    registry.anddHandler(occpWebSocketHandler(), ~~) 위 주소에 연결된 클래스
                                    .setAllowedOrigins("*"): CORS 설정
                                     registry.addHandler(...): 실제 WebSocket 서버의 엔드포인트(URL)와 **핸들러(처리 로직)**를 연결함.
 3.createWebSocketContainer 버퍼설정
 4. ocppWebSocketHandler() 빈으로 등록
 */

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler ocppWebSocketHandler;
    private final WebSocketHandler reactWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(ocppWebSocketHandler, "/ocpp")
                .setAllowedOrigins("*");

        registry.addHandler(reactWebSocketHandler, "/react")
                .setAllowedOrigins("*");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(65536); // Larger buffer for OCPP 2.0.1
        container.setMaxBinaryMessageBufferSize(65536);
        return container;
    }
}
