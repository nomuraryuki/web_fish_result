package com.example.springfishproject.repository;

import com.example.springfishproject.record.FishingResultsRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgFishingResutsDao implements FishingResultsDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public List<FishingResultsRecord> findAll() {
        return jdbcTemplate.query("SELECT fish_name,fish_size,dating,weather,tide_kinds,place_name,description FROM fishing_results JOIN tide ON fishing_results.id = tide.id JOIN place ON fishing_results.id = place.id ORDER BY fishing_results.id",
                new DataClassRowMapper<>(FishingResultsRecord.class));
    }
}
