package com.example.quaterback.api.domain.price.repository;

import com.example.quaterback.api.domain.price.constant.Season;
import com.example.quaterback.api.domain.price.constant.TimeSlot;
import com.example.quaterback.api.domain.price.entity.KepcoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KepcoRepository extends JpaRepository<KepcoPrice, Long> {
    KepcoPrice findBySeasonAndTimeSlot(Season season, TimeSlot timeSlot);
}
