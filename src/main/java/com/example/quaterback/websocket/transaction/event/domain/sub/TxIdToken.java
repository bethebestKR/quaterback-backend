package com.example.quaterback.websocket.transaction.event.domain.sub;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TxIdToken {
    private String idToken;
    private String type;
}
