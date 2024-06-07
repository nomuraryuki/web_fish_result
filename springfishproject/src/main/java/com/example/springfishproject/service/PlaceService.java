package com.example.springfishproject.service;

import com.example.springfishproject.record.PlaceRecord;
import com.example.springfishproject.record.TideRecord;

import java.util.List;

public interface PlaceService {
    List<PlaceRecord> findAll();

    PlaceRecord findIdByName(String place_name);
}
