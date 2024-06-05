package com.example.springfishproject.repository;

import com.example.springfishproject.record.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PgUserDao implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public UserRecord findUser(String loginId, String password) {
        var param = new MapSqlParameterSource();
        param.addValue("loginId", loginId);
        param.addValue("password", password);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE login_id = :loginId AND password = :password" , param, new DataClassRowMapper<>(UserRecord.class));
        return list.isEmpty() ? null : list.get(0);

    }

    @Override
    public int insertUser(UserRecord userRecord){
        var param = new MapSqlParameterSource();
        param.addValue("login_id", userRecord.login_id());
        param.addValue("password", userRecord.password());
        param.addValue("name", userRecord.name());
        param.addValue("role", 2);
        try {
            return jdbcTemplate.update("INSERT INTO users (login_id, password, name, role) values(:login_id, :password, :name, :role)", param);
        }catch (RuntimeException e){
            return -1;
        }
    }
}
