package com.example.springfishproject.service;

import com.example.springfishproject.Exception.ProductDuplicationIdException;
import com.example.springfishproject.Exception.ProductNotFoundException;
import com.example.springfishproject.record.FishingResultsRecord;
import com.example.springfishproject.record.InsertFishingResultRecord;
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

    @Override
    public List<FishingResultsRecord> findByPlace(String place) {
        return fishingResultsDao.findByPlace(place);
    }

    @Override
    public FishingResultsRecord findById(int id) {

        if (fishingResultsDao.findById(id) == null) throw new ProductNotFoundException();

        else {return fishingResultsDao.findById(id);}
    }

    @Override
    public int insert(InsertFishingResultRecord insertFishingResultRecord){
//        if (fishingResultsDao.insert(insertFishingResultRecord) == -1) throw new ProductDuplicationIdException();
//        else{return fishingResultsDao.insert(insertFishingResultRecord);}
        return fishingResultsDao.insert(insertFishingResultRecord);
    }

    @Override
    public int update(InsertFishingResultRecord insertFishingResultRecord) {
        return fishingResultsDao.update(insertFishingResultRecord);
    }


    @Override
    public int delete(int id) {
        try{
            return fishingResultsDao.delete(id);
        }catch (Exception e){
            return -1;
        }
    }


}
