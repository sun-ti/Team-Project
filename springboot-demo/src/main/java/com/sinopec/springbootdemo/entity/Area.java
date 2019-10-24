package com.sinopec.springbootdemo.entity;

public class Area {
    private String areaname;
    private String areaid;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String stationid) {
        this.areaid = stationid;
    }
}
