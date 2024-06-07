package com.example.springfishproject.service;

import com.example.springfishproject.record.TideRecord;

import java.util.List;

public interface TideService {
    List<TideRecord> findAll();

    TideRecord findIdByName(String tide_kinds);
}
