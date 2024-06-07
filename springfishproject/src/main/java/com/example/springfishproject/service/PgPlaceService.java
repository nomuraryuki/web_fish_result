package com.example.springfishproject.service;

import com.example.springfishproject.record.PlaceRecord;
import com.example.springfishproject.repository.PlaceDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PgPlaceService implements PlaceService{
    @Autowired
    private PlaceDao placeDao;

    @Override
    public List<PlaceRecord> findAll(){
        return placeDao.findAll();
    }

    @Override
    public  PlaceRecord findIdByName(String place_name){return placeDao.findIdByName(place_name);}

}
