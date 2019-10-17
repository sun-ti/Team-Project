package com.sinopec.springbootdemo.service;


import com.sinopec.springbootdemo.entity.Camera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class CameraService {

    @Autowired
    private HttpService httpService;

    public void getData(Map<String, String> params, Camera camera) {

        String url = "http://" + "49.233.36.65:8080" + "/ExtractLoadV01/Device";

        HttpMethod method = HttpMethod.GET;

        params.put("oper", "0");
        params.put("deviceip",camera.getDeviceip());
        params.put("note",camera.getNote());
        params.put("kind",camera.getKind());
        params.put("stationid",camera.getStationid());

         httpService.client(url, method, params);
    }
    public void delData(Map<String, String> params, String uuid) {

        String url = "http://" + "49.233.36.65:8080" + "/ExtractLoadV01/Device";

        HttpMethod method = HttpMethod.GET;

        params.put("oper", "1");
        params.put("uuid",uuid);
        httpService.client(url, method, params);
    }
}
