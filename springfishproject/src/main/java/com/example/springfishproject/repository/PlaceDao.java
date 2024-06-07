package com.example.springfishproject.repository;

import com.example.springfishproject.record.PlaceRecord;

import java.util.List;

public interface PlaceDao {
    List<PlaceRecord> findAll();

    PlaceRecord findIdByName(String place_name);
}
