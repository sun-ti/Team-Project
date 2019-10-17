package com.sinopec.springbootdemo.entity;

import java.security.PrivateKey;

public class Camera {
    private  String deviceip;
    private String note;
    private String kind;
    private String   stationid;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDeviceip() {
        return deviceip;
    }

    public void setDeviceip(String deviceip) {
        this.deviceip = deviceip;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }
}
