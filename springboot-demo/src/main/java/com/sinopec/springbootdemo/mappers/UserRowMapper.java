package com.sinopec.springbootdemo.mappers;

import com.sinopec.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs,int i) throws SQLException {
        User user = new User();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        user.setAutoId(rs.getString("USER_AutoID"));
        user.setUuid(rs.getString("USER_UUID"));
        user.setUsername(rs.getString("USERNAME"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setCreateTime(rs.getDate("USER_CREATE_TIME"));
        user.setRefreshTime(rs.getDate("USER_REFRESH_TIME"));
        user.setRefreshMark(rs.getInt("USER_REFRESH_MARK"));
        user.setDelFlag(rs.getInt("USER_DEL"));
        return user;
    }
}
