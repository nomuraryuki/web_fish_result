package com.example.springfishproject.repository;

import com.example.springfishproject.record.TideRecord;

import java.util.List;

public interface TideDao {

    List<TideRecord> findAll();

    TideRecord findIdByName(String tide_kinds);

}
