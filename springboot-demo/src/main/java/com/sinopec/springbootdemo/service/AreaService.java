package com.sinopec.springbootdemo.service;

import com.sinopec.springbootdemo.entity.Station;
import com.sinopec.springbootdemo.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AreaService {

    @Autowired
    private HttpService httpService;

    public void addareaData(Map<String, String> params, Area area) {

        String url = "http://" + "49.233.36.65:8080" + "/ExtractLoadV01/";

        HttpMethod method = HttpMethod.GET;

        params.put("oper", "?");
        params.put("name",area.getAreaname());
        params.put("id",area.getAreaid());

        httpService.client(url, method, params);
    }

    public void delareaData(Map<String, String> params, String uuid) {

        String url = "http://" + "49.233.36.65:8080" + "/ExtractLoadV01/";

        HttpMethod method = HttpMethod.GET;

        params.put("oper", "?");
        params.put("uuid",uuid);
        httpService.client(url, method, params);
    }

    public void addstationData(Map<String, String> params, Station station) {

        String url = "http://" + "49.233.36.65:8080" + "/ExtractLoadV01/";

        HttpMethod method = HttpMethod.GET;

        params.put("oper", "?");
        params.put("name",station.getStationname());
        params.put("short_name",station.getStation_short_name());
        params.put("id",station.getStationid());
        params.put("m_id",station.getStation_manage_id());
        params.put("company",station.getBelongtocompany());
        params.put("address",station.getAddress());
        params.put("phone",station.getPhone());

        httpService.client(url, method, params);
    }

    public void delstationData(Map<String, String> params, String uuid) {

        String url = "http://" + "49.233.36.65:8080" + "/ExtractLoadV01/";

        HttpMethod method = HttpMethod.GET;

        params.put("oper", "?");
        params.put("uuid",uuid);
        httpService.client(url, method, params);
    }
}
