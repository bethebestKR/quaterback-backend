package com.example.quaterback.websocket.sub;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubIdToken {
    private String idToken;
    private String type;
}
