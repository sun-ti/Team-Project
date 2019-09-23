package com.sinopec.springbootdemo.mappers;

import com.sinopec.springbootdemo.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PermissionRowMapper implements RowMapper<Permission> {

    @Override
    public Permission mapRow(ResultSet rs, int i) throws SQLException {
        Permission permission = new Permission();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        permission.setAutoId(rs.getString("PERMISSION_AutoID"));
        permission.setUuid(rs.getString("PERMISSION_UUID"));
        permission.setName(rs.getString("PERMISSION_NAME"));
        permission.setCreateTime(rs.getDate("PERMISSION_CREATE_TIME"));
        permission.setDelFlag(rs.getInt("PERMISSION_DEL"));
        permission.setParentUuid(rs.getString("PERMISSION_PARENT_UUID"));
        permission.setUrl(rs.getString("PERMISSION_URL"));
        permission.setType(rs.getInt("PERMISSION_TYPE"));
        return permission;
    }
}
