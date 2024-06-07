package com.example.springfishproject.service;

import com.example.springfishproject.record.TideRecord;
import com.example.springfishproject.repository.TideDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PgTideService implements TideService{
    @Autowired
    private TideDao tideDao;

    @Override
    public List<TideRecord> findAll(){
        return tideDao.findAll();
    }

    @Override
    public  TideRecord findIdByName(String tide_kinds){return tideDao.findIdByName(tide_kinds);}


}
