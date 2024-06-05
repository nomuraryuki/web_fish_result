package com.example.springfishproject.service;

import com.example.springfishproject.record.UserRecord;

public interface UserService {
    UserRecord findUser(String loginId, String password);
    int insertUser(UserRecord userRecord);
}
