package com.example.springfishproject.service;

import com.example.springfishproject.Exception.ProductNotFoundException;
import com.example.springfishproject.record.UserRecord;
import com.example.springfishproject.repository.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PgUserService implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public UserRecord findUser(String loginId, String password){
        if (userDao.findUser(loginId,password) == null) throw new ProductNotFoundException();

        else {return userDao.findUser(loginId,password);}
//        return userDao.findUser(loginId,password);
    }

    @Override
    public int insertUser(UserRecord userRecord){
        if (userDao.insertUser(userRecord) == -1) throw new ProductNotFoundException();
        else{return userDao.insertUser(userRecord);}
    }
}
