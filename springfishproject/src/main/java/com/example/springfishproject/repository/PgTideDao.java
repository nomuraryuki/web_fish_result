package com.example.springfishproject.repository;

import com.example.springfishproject.record.TideRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgTideDao implements TideDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<TideRecord> findAll(){
        return jdbcTemplate.query("SELECT * FROM tide ORDER BY id",
                new DataClassRowMapper<>(TideRecord.class));

    }

    @Override
    public TideRecord findIdByName(String tide_kinds){
        var param = new MapSqlParameterSource();
        param.addValue("tide_kinds", tide_kinds);
        var list = jdbcTemplate.query("SELECT * FROM tide WHERE tide_kinds = :tide_kinds" ,
                param, new DataClassRowMapper<>(TideRecord.class));;
        return list.isEmpty() ? null : list.get(0);
    }
}
