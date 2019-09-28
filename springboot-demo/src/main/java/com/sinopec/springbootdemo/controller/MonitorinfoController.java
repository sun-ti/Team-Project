package com.sinopec.springbootdemo.controller;

import com.sinopec.springbootdemo.entity.Monitorinfo;
import com.sinopec.springbootdemo.myUtil.ConfigUtil;
import com.sinopec.springbootdemo.myUtil.LayuiTableResultUtil;
import com.sinopec.springbootdemo.myUtil.RequiredUtil;
import com.sinopec.springbootdemo.service.HttpService;
import com.sinopec.springbootdemo.service.MonitorinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("monitorinfo")
public class MonitorinfoController {

    @Autowired
    private HttpService httpService;


    @Autowired
    private Environment env;


    @RequestMapping("/human_face")
    public String humanFace() {
        return "monitorinfo/human_face";
    }

    // 人脸查询
    @ResponseBody
    @RequestMapping("/humanFaceList")
    public String getHumanFaceList(HttpServletRequest request) throws IOException {

        Map<String, String> params = new HashMap<>();

        // 范围日期筛选
        if (request.getParameter("startDate") != null && !request.getParameter("startDate").isEmpty()) {
            params.put("oper", "4");
            params.put("start", request.getParameter("startDate"));
            params.put("end", request.getParameter("endDate"));
        } else {
            params.put("oper", "0");
            params.put("lcommand","4370");
        }
        return getInfo(request, params);
    }

    @RequestMapping("/car_plate")
    public String carPlate() {
        return "monitorinfo/car_plate";
    }

    // 车牌查询
    @ResponseBody
    @RequestMapping("/carPlateList")
    public String getCarPlateList(HttpServletRequest request) throws IOException {

        Map<String, String> params = new HashMap<>();

        // 范围日期筛选
        if (request.getParameter("startDate") != null && !request.getParameter("startDate").isEmpty()) {
            params.put("oper", "1");
            params.put("start", request.getParameter("startDate"));
            params.put("end", request.getParameter("endDate"));
        } else {
            params.put("oper", "0");
            params.put("lcommand","12368");
        }
        return getInfo(request, params);
    }

    @RequestMapping("/human_flow")
    public String humanFlow() {
        return "monitorinfo/human_flow";
    }

    // 人流查询
    @ResponseBody
    @RequestMapping("/humanFlowList")
    public String getHumanFlow(HttpServletRequest request) throws IOException {
        Map<String, String> params = new HashMap<>();

        // 范围日期筛选
        if (request.getParameter("startDate") != null && !request.getParameter("startDate").isEmpty()) {
            params.put("oper", "4");
            params.put("start", request.getParameter("startDate"));
            params.put("end", request.getParameter("endDate"));
        } else {
            params.put("oper", "0");
            params.put("lcommand","4355");
        }
        return getInfo(request, params);
    }

    public String getInfo(HttpServletRequest request, Map<String, String> params) {

        String url = "http://" + env.getProperty("myConfig.mysqlAddr") + "/ExtractLoadV01/Monitor";

        HttpMethod method = HttpMethod.GET;


        params.put("limit", request.getParameter("limit").trim());
        params.put("page", request.getParameter("page").trim());
        params.put("stationid", "jyxd");

        return httpService.client(url, method, params);
    }
}
