package com.sinopec.springbootdemo.controller;

import com.sinopec.springbootdemo.myUtil.LayuiTableResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinopec.springbootdemo.service.HttpService;

@Controller
@RequestMapping("/tone")
public class ToneController {
    @Autowired
    private HttpService httpService;

    @Autowired
    private Environment env;

    @RequestMapping("/black_add")
    public String add() {
        return "blacklist/project_add";
    }

    @RequestMapping("/black_blackpost")
    public String blackpost() {
        return "blacklist/project_blackpost";
    }

    @RequestMapping("/distribution_list")
    public String distributionList() {
        return "oil_unloading_operate/distribution_list";
    }

    @RequestMapping("/black_record")
    public String record() {
        return "blacklist/project_record";
    }

    @RequestMapping("/camera")
    public String carmera() {
        return "camera/project_camera";
    }

    @RequestMapping("/client")
    public String client() {
        return "client/project_client";
    }

    @RequestMapping("/interface")
    public String interf() {
        return "interface/project_interface";
    }

    @RequestMapping("/log")
    public String log1() {
        return "log/project_log";
    }

    @RequestMapping("/oil")
    public String oil() {
        return "oil/project_oil";
    }

    @RequestMapping("/station")
    public String station() {
        return "station/project_station";
    }
    //摄像头查询
    @ResponseBody
    @RequestMapping("/cameraDate")
    public String getCameraDate(HttpServletRequest request) throws IOException {
        String url = "http://" + env.getProperty("myConfig.mysqlAddr") + "/ExtractLoadV01/Device";

        HttpMethod method = HttpMethod.GET;
        Map<String, String> params = new HashMap<>();



            params.put("oper", "3");
            params.put("limit", request.getParameter("limit").trim());
            params.put("page", request.getParameter("page").trim());


        return httpService.client(url, method, params);

    }
    //站点查询
    @ResponseBody
    @RequestMapping("/stationDate")
    public String getStationDate(HttpServletRequest request) throws IOException {
        String url = "http://" + env.getProperty("myConfig.mysqlAddr") + "/ExtractLoadV01/Station";

        HttpMethod method = HttpMethod.GET;
        Map<String, String> params = new HashMap<>();

        params.put("limit", request.getParameter("limit").trim());
        params.put("page", request.getParameter("page").trim());
        params.put("oper", "3");


        return httpService.client(url, method, params);

    }
    //卸油配送单查询
    @ResponseBody
    @RequestMapping("/distributionDate")
    public String getDistributionDate(HttpServletRequest request) throws IOException {
        String url = "http://" + env.getProperty("myConfig.mysqlAddr") + "ExtractLoadV01/YWY";

        HttpMethod method = HttpMethod.GET;
        Map<String, String> params = new HashMap<>();

        params.put("limit", request.getParameter("limit").trim());
        params.put("page", request.getParameter("page").trim());
        params.put("oper", "3");


        return httpService.client(url, method, params);

    }
    //加油机查询
    @ResponseBody
    @RequestMapping("/oilDate")
    public String getOilDate(HttpServletRequest request) throws IOException {
        String url = "http://" + env.getProperty("myConfig.mysqlAddr") + "ExtractLoadV01/Tanker";

        HttpMethod method = HttpMethod.GET;
        Map<String, String> params = new HashMap<>();

        params.put("limit", request.getParameter("limit").trim());
        params.put("page", request.getParameter("page").trim());
        params.put("oper", "3");
        return httpService.client(url, method, params);
    }
    //黑名单查询
    @ResponseBody
    @RequestMapping("/blacklistDate")
    public String getblacklistDate(HttpServletRequest request) throws IOException {
        String url = "http://" + env.getProperty("myConfig.mysqlAddr") + "ExtractLoadV01/Monitor";

        HttpMethod method = HttpMethod.GET;
        Map<String, String> params = new HashMap<>();

        params.put("limit", request.getParameter("limit").trim());
        params.put("page", request.getParameter("page").trim());
        params.put("oper", "21");
        params.put("year", "2019");
        return httpService.client(url, method, params);
    }

}


