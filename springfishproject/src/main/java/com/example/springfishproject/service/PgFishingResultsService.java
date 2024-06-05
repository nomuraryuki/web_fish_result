package com.example.springfishproject.service;

import com.example.springfishproject.record.FishingResultsRecord;
import com.example.springfishproject.repository.FishingResultsDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PgFishingResultsService implements FishingResultsService{

    @Autowired
    private FishingResultsDao fishingResultsDao;

    @Override
    public List<FishingResultsRecord> findAll() {
        return fishingResultsDao.findAll();
    }

}
