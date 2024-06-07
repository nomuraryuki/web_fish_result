package com.example.springfishproject.repository;

import com.example.springfishproject.record.PlaceRecord;
import com.example.springfishproject.record.TideRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgPlaceDao implements PlaceDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<PlaceRecord> findAll(){
        return jdbcTemplate.query("SELECT * FROM place ORDER BY id",
                new DataClassRowMapper<>(PlaceRecord.class));
    }

    @Override
    public PlaceRecord findIdByName(String place_name){
        var param = new MapSqlParameterSource();
        param.addValue("place_name", place_name);
        var list = jdbcTemplate.query("SELECT * FROM place WHERE place_name = :place_name" ,
                param, new DataClassRowMapper<>(PlaceRecord.class));;
        return list.isEmpty() ? null : list.get(0);
    }
}
