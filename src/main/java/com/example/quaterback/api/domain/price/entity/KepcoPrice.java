package com.example.quaterback.api.domain.price.entity;

import com.example.quaterback.api.domain.price.constant.Season;
import com.example.quaterback.api.domain.price.constant.TimeSlot;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kepco_price")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KepcoPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Season season;

    @Enumerated(EnumType.STRING)
    private TimeSlot timeSlot;

    private double pricePerKwh;
}

