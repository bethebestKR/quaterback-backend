package com.example.quaterback.api.domain.price.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cs_price")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricePerMwh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double pricePerMwh;
    private LocalDateTime updatedDateTime;

    public static PricePerMwh from(double pricePerMwh) {
        return PricePerMwh.builder()
                .pricePerMwh(pricePerMwh)
                .updatedDateTime(LocalDateTime.now())
                .build();
    }
}
