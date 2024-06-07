package com.example.springfishproject.service;

import com.example.springfishproject.record.FishingResultsRecord;
import com.example.springfishproject.record.InsertFishingResultRecord;

import java.util.List;

public interface FishingResultsService {
    List<FishingResultsRecord> findAll();

    List<FishingResultsRecord> findByPlace(String place);

    FishingResultsRecord findById(int id);

    int insert(InsertFishingResultRecord insertFishingResultRecord);

    int update(InsertFishingResultRecord insertFishingResultRecord);

    int delete(int id);
}
