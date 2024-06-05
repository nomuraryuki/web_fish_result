package com.example.springfishproject.record;

public record FishingResultsRecord(
        String fish_name,
        double fish_size,
        String dating,
        String weather,
        String tide_kinds,
        String place_name,
        String description) {}
