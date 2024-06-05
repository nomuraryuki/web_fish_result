package com.example.springfishproject.repository;

import com.example.springfishproject.record.FishingResultsRecord;

import java.util.List;

public interface FishingResultsDao {
    List<FishingResultsRecord> findAll();
}
