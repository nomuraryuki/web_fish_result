package com.example.springfishproject.record;

public record InsertFishingResultRecord(int id,
                                        String fish_name,
                                        double fish_size,
                                        String dating,
                                        String weather,
                                        int tide_id,
                                        int place_id,
                                        String description,
                                        String img_path) {
}
