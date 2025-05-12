package com.example.quaterback.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

public class StationNameValidator {
    private static final Pattern STATION_ID_PATTERN = Pattern.compile("^station-\\d+$");

    public static void validateStationName(String stationId){
        if(stationId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "stationName을 적읍시다.");
        }
    }
}
