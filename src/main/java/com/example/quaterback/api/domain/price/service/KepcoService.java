package com.example.quaterback.api.domain.price.service;

import com.example.quaterback.api.domain.price.constant.Season;
import com.example.quaterback.api.domain.price.constant.TimeSlot;
import com.example.quaterback.api.domain.price.repository.KepcoRepository;
import com.example.quaterback.api.feature.dashboard.dto.response.KepcoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class KepcoService {

    private final KepcoRepository kepcoRepository;

    public KepcoResponse getCurrentKepcoPrice() {
        LocalDateTime now = LocalDateTime.now();
        Season season = determineSeason(now);
        TimeSlot timeSlot = determineTimeSlot(now);
        double curPrice = kepcoRepository.findBySeasonAndTimeSlot(season, timeSlot).getPricePerKwh() / 1000.0;
        curPrice = Math.round(curPrice * 100) / 100.0;
        return new KepcoResponse(curPrice);
    }

    public Season determineSeason(LocalDateTime dateTime) {
        int month = dateTime.getMonthValue();

        if (month >= 6 && month <= 8) {
            return Season.SUMMER;
        } else if (month >= 11 || month <= 2) {
            return Season.WINTER;
        } else return Season.SPRING_FALL;
    }

    public TimeSlot determineTimeSlot(LocalDateTime dateTime) {
        int hour = dateTime.getHour();

        if (hour >= 16 && hour < 22) {
            return TimeSlot.ON_PEAK;
        } else if (hour >= 8 && hour < 16) {
            return TimeSlot.MID_PEAK;
        } else {
            return TimeSlot.OFF_PEAK;
        }
    }
}
