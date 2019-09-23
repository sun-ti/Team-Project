package com.sinopec.springbootdemo.controller;

import com.sinopec.springbootdemo.entity.Monitorinfo;
import com.sinopec.springbootdemo.myUtil.LayuiTableResultUtil;
import com.sinopec.springbootdemo.myUtil.RequiredUtil;
import com.sinopec.springbootdemo.service.HttpService;
import com.sinopec.springbootdemo.service.MonitorinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("monitorinfo")
public class MonitorinfoController {

    @Autowired
    private MonitorinfoService monitorinfoService;

    @Autowired
    private HttpService httpService;

    private String monitorinfoType;

    @RequestMapping("/human_face")
    public String humanFace() {
        return "monitorinfo/human_face";
    }

    @ResponseBody
    @RequestMapping("/humanFaceList")
    public String getHumanFaceList(HttpServletRequest request) throws IOException {

        monitorinfoType="4370";
        return getInfo(request);
    }

    @RequestMapping("/car_plate")
    public String carPlate() {
        return "monitorinfo/car_plate";
    }

    @ResponseBody
    @RequestMapping("/carPlateList")
    public String getCarPlateList(HttpServletRequest request) throws IOException {

        monitorinfoType="12368";
        return getInfo(request);
    }

    @RequestMapping("/human_flow")
    public String humanFlow() {
        return "monitorinfo/human_flow";
    }

    @ResponseBody
    @RequestMapping("/humanFlowList")
    public String getHumanFlow(HttpServletRequest request) throws IOException {

        monitorinfoType="4355";
        return getInfo(request);
    }

    public String getInfo(HttpServletRequest request){

        String url = "http://192.168.3.120:8080/ExtractLoadV01/Query";

        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("limit", request.getParameter("limit").trim());
        params.add("page", request.getParameter("page").trim());
        params.add("lcommand", monitorinfoType);

        if (request.getParameter("dateRange") != null) {
            params.add("dateRange",request.getParameter("dateRange"));
        }

        return httpService.client(url,method,params);
    }
}
