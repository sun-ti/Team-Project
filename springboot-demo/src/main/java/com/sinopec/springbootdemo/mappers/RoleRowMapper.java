package com.sinopec.springbootdemo.mappers;

import com.sinopec.springbootdemo.entity.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs,int i) throws SQLException {
        Role role = new Role();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        role.setRoleUuid(rs.getString("ROLE_UUID"));
        role.setRoleName(rs.getString("ROLE_NAME"));
        role.setCreateTime(rs.getDate("ROLE_CREATE_TIME"));
        role.setDelFlag(rs.getInt("ROLE_DEL"));
        return role;
    }
}
