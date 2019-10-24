package com.sinopec.springbootdemo.entity;

public class Oil {
    private String uuid;
    private String oilid;
    private String oilname;
    private String short_name;

    public String getOilid(){return uuid;}

    public String getUuid(){return oilid;}

    public String getOilname(){return oilname;}

    public String getShort_name(){return short_name;}
}
