package com.example.springfishproject.repository;

import com.example.springfishproject.record.FishingResultsRecord;
import com.example.springfishproject.record.InsertFishingResultRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class PgFishingResutsDao implements FishingResultsDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public List<FishingResultsRecord> findAll() {
        return jdbcTemplate.query("SELECT fishing_results.id,fish_name,fish_size,dating,weather,tide_kinds,place_name,description,img_path FROM fishing_results JOIN tide ON fishing_results.tide_id = tide.id JOIN place ON fishing_results.place_id = place.id ORDER BY fishing_results.id",
                new DataClassRowMapper<>(FishingResultsRecord.class));
    }

    @Override
    public  List<FishingResultsRecord> findByPlace(String place){
        var param = new MapSqlParameterSource();
        param.addValue("place_name", place);
        return jdbcTemplate.query("SELECT fishing_results.id,fish_name,fish_size,dating,weather,tide_kinds,place_name,description,img_path FROM fishing_results JOIN tide ON fishing_results.tide_id = tide.id JOIN place ON fishing_results.place_id = place.id WHERE place_name LIKE :place_name ORDER BY fishing_results.id"
                , param,new DataClassRowMapper<>(FishingResultsRecord.class));
    }

    @Override
    public FishingResultsRecord findById(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT fishing_results.id AS id,fish_name,fish_size,dating,weather,tide_kinds,place_name,description,img_path FROM fishing_results JOIN tide ON fishing_results.tide_id = tide.id JOIN place ON fishing_results.place_id = place.id WHERE fishing_results.id = :id ORDER BY fishing_results.id" ,
                param, new DataClassRowMapper<>(FishingResultsRecord.class));
        return list.isEmpty() ? null : list.get(0);

    }

    @Override
    public int insert(InsertFishingResultRecord insertFishingResultRecord) {
        var param = new MapSqlParameterSource();

        String dateString = insertFishingResultRecord.dating();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            if(dateString.equals(""))param.addValue("dating", null);
            else {
                Date date = dateFormat.parse(dateString);
                param.addValue("dating", date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        param.addValue("fish_name", insertFishingResultRecord.fish_name());
        param.addValue("fish_size", insertFishingResultRecord.fish_size());
//        param.addValue("dating", insertFishingResultRecord.dating());
        param.addValue("weather", insertFishingResultRecord.weather());
        param.addValue("tide_id", insertFishingResultRecord.tide_id());
        param.addValue("place_id",insertFishingResultRecord.place_id());
        param.addValue("description", insertFishingResultRecord.description());
        param.addValue("img_path", insertFishingResultRecord.img_path());
        try {
            return jdbcTemplate.update("INSERT INTO fishing_results(fish_name,fish_size,dating,weather,tide_id,place_id,description,img_path) VALUES( :fish_name, :fish_size, :dating, :weather, :tide_id, :place_id, :description, :img_path)", param);
        }catch (RuntimeException e){
            return -1;
        }

    }

    @Override
    public int update(InsertFishingResultRecord insertFishingResultRecord){
        var param = new MapSqlParameterSource();

        String dateString = insertFishingResultRecord.dating();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            if(dateString.equals(""))param.addValue("dating", null);
            else {
                Date date = dateFormat.parse(dateString);
                param.addValue("dating", date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        param.addValue("id", insertFishingResultRecord.id());
        param.addValue("fish_name", insertFishingResultRecord.fish_name());
        param.addValue("fish_size", insertFishingResultRecord.fish_size());
        param.addValue("weather", insertFishingResultRecord.weather());
        param.addValue("tide_id", insertFishingResultRecord.tide_id());
        param.addValue("place_id", insertFishingResultRecord.place_id());
        param.addValue("description",insertFishingResultRecord.description());
        param.addValue("img_path", insertFishingResultRecord.img_path());
        System.out.println(insertFishingResultRecord.id());
        return jdbcTemplate.update("UPDATE fishing_results SET fish_name = :fish_name,fish_size = :fish_size,dating = :dating, weather = :weather, tide_id = :tide_id, place_id =:place_id, description=:description, img_path = :img_path WHERE id = :id", param);
    }

    @Override
    public int delete(int id){
        var param = new MapSqlParameterSource();

        param.addValue("id", id);

        return jdbcTemplate.update("DELETE from fishing_results WHERE id = :id", param);
    }
}
