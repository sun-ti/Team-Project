package com.sinopec.springbootdemo.service;

import com.sinopec.springbootdemo.dao.UserDao;
import com.sinopec.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getUserByUserName(String userName) {
        return userDao.queryUserByUserName(userName);
    }

    public List<User> getAllUserPage(int limit, int page) {
        return userDao.queryAllUserListPage(limit, page);
    }

    public int getUserCount() {
        return userDao.getUserCount();
    }

    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    public void deleteUserByUuid(String uuid) {
        userDao.deleteUserByUuid(uuid);
    }
}
