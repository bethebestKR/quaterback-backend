package com.example.quaterback.websocket.transaction.event.domain.sub;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionInfo {
    private String transactionId;
}
