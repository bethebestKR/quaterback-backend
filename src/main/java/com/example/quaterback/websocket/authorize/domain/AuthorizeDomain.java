package com.example.quaterback.websocket.authorize.domain;

import com.example.quaterback.websocket.sub.SubIdToken;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorizeDomain {
    private String messageTypeId;
    private String messageId;
    private String action;

    private SubIdToken authIdToken;

    public String extractIdToken() {
        return authIdToken.getIdToken();
    }
}
