package com.example.quaterback.api.domain.price.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "price")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricePerMwh {
    @Id
    private long id;

    private double pricePerMwh;
    private LocalDateTime updatedDateTime;
}
