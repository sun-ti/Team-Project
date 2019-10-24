package com.sinopec.springbootdemo.entity;

public class Station {
    private String stationname;
    private String station_short_name;
    private String stationid;
    private String station_manage_id;
    private String belongtocompany;
    private String address;
    private String phone;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    public String getStation_short_name() {
        return station_short_name;
    }

    public void setStation_short_name(String station_short_name) {
        this.station_short_name = station_short_name;
    }

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }

    public String getStation_manage_id() {
        return station_manage_id;
    }

    public void setStation_manage_id(String station_manage_id) {
        this.station_manage_id = station_manage_id;
    }

    public String getBelongtocompany() {
        return belongtocompany;
    }

    public void setBelongtocompany(String belongtocompany) {
        this.belongtocompany = belongtocompany;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
