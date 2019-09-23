package com.sinopec.springbootdemo.mappers;

import com.sinopec.springbootdemo.entity.Monitorinfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MonitorinfoRowMapper implements RowMapper<Monitorinfo> {

    @Override
    public Monitorinfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Monitorinfo monitorinfo = new Monitorinfo();
        monitorinfo.setMonitorinfoUuid(rs.getString("uuid"));
        monitorinfo.setCreateTime(rs.getTimestamp("datetime"));
        monitorinfo.setDeviceIp(rs.getString("deviceip"));
        monitorinfo.setInfoType(rs.getString("lcommand"));
        monitorinfo.setInfoData(rs.getString("errMsg"));
        monitorinfo.setStationId(rs.getString("stationId"));
        monitorinfo.setPic1Address(rs.getString("pic1"));
        monitorinfo.setPic2Address(rs.getString("pic2"));
        monitorinfo.setState(rs.getInt("state"));
        return monitorinfo;
    }
}
