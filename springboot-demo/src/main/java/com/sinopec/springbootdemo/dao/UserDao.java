package com.sinopec.springbootdemo.dao;

import com.sinopec.springbootdemo.entity.User;
import com.sinopec.springbootdemo.mappers.UserRowMapper;
import com.sinopec.springbootdemo.myUtil.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DateUtil dateUtil;

    public User queryUserByUserName(String userName) {
        String sql = "SELECT * FROM user WHERE USERNAME=? AND USER_DEL=0";
        User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), userName);
        return user;
    }

    public List<User> queryAllUserListPage(int limit, int page) {
        String sql = "SELECT * FROM user WHERE USER_DEL=0 ORDER BY USER_AutoID limit " + (page - 1) * limit + "," + limit;
        List<User> userList = jdbcTemplate.query(sql, new UserRowMapper());
        return userList;
    }

    public int getUserCount() {
        String sql = "SELECT count(USER_AutoID) FROM user WHERE USER_DEL=0";
        Integer userCount = jdbcTemplate.queryForObject(sql, Integer.class);
        return userCount;
    }

    public void insertUser(User user) {
        String sql = "INSERT INTO user VALUES(null, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), dateUtil.getNowDateString(), dateUtil.getNowDateString(), 0, 0, UUID.randomUUID().toString());
    }

    public void deleteUserByUuid(String uuid) {
        String sql = "UPDATE user set USER_DEL=1 WHERE USER_UUID=?";
        jdbcTemplate.update(sql, uuid);
    }
}
