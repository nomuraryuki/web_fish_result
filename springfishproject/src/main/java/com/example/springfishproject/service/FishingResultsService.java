package com.example.springfishproject.service;

import com.example.springfishproject.record.FishingResultsRecord;

import java.util.List;

public interface FishingResultsService {
    List<FishingResultsRecord> findAll();
}
