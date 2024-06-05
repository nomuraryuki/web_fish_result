package com.example.springfishproject.repository;

import com.example.springfishproject.record.UserRecord;

public interface UserDao {
    UserRecord findUser(String loginId, String passeord);
    int insertUser(UserRecord userRecord);
}
