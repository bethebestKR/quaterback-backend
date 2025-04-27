package com.example.quaterback.websocket.boot.notification.domain.sub;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {
    private Double latitude;
    private Double longitude;
    private String address;
}
