package com.sinopec.springbootdemo.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Monitorinfo {

    private String monitorinfoUuid;
    private String stationId;
    private String infoType;
    private String deviceIp;
    private String infoData;
    private Date createTime;

    private String pic1Address;
    private String pic2Address;
    private int state;

    public String getMonitorinfoUuid() {
        return monitorinfoUuid;
    }

    public void setMonitorinfoUuid(String monitorinfoUuid) {
        this.monitorinfoUuid = monitorinfoUuid;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getInfoData() {
        return infoData;
    }

    public void setInfoData(String infoData) {
        this.infoData = infoData;
    }

    public Date getCreateTime() {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String str = df.format(createTime);
        try {
//            System.out.println(str);
            Date tmpDate = df.parse(str);
            return tmpDate;
        }
        catch (ParseException e) {
            return createTime;
        }
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPic1Address() {
        return pic1Address;
    }

    public void setPic1Address(String pic1Address) {
        this.pic1Address = pic1Address;
    }

    public String getPic2Address() {
        return pic2Address;
    }

    public void setPic2Address(String pic2Address) {
        this.pic2Address = pic2Address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
