package com.example.springfishproject.record;

public record FishingResultsRecord(
        int id,
        String fish_name,
        double fish_size,
        String dating,
        String weather,
        String tide_kinds,
        String place_name,
        String description,
        String img_path) {}
