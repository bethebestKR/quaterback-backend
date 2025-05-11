package com.example.quaterback.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

public class StationIdValidator {
    private static final Pattern STATION_ID_PATTERN = Pattern.compile("^station-\\d+$");

    public static void validateStationId(String stationId){
        if(stationId == null || !STATION_ID_PATTERN.matcher(stationId).matches()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "stationId는 'station-숫자' 형식이어야 합니다.");
        }
    }
}
