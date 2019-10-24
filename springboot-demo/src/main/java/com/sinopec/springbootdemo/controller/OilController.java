package com.sinopec.springbootdemo.controller;

import com.sinopec.springbootdemo.entity.Area;
import com.sinopec.springbootdemo.entity.Oil;
import com.sinopec.springbootdemo.entity.Station;
import com.sinopec.springbootdemo.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sinopec.springbootdemo.service.HttpService;

@Controller
@RequestMapping("/oilmanagement")
public class OilController {
    @Autowired
    private AreaService areaService;

    @Autowired
    private HttpService httpService;

    @RequestMapping("/oil")
    public String oil() {
        return "oil/project_oil";
    }

    //----------------------------------------片区增加
    @RequestMapping(value = "addOil", method = RequestMethod.GET)
    public String oilAdd(Model model) {
        model.addAttribute("list", new Oil());
        //?改成相对路径错误
        return "oil/oil_add";
    }

    @RequestMapping(value = "addOil", method = RequestMethod.POST)
    public String oilAdd(@ModelAttribute Oil oil) {
        Map<String, String> params = new HashMap<>();

        String url = "http://" + "49.233.36.65:8080" + "/ExtractLoadV01/";

        HttpMethod method = HttpMethod.GET;

        params.put("oper", "?");
        params.put("4",oil.getUuid());


        httpService.client(url, method, params);
       /* oilService.addareaData(params,oil);*/
        //必须redirect,否则error page
        return "redirect:/oilmanagement/oil";
    }
    //-------------------------------------------删除片区
    @ResponseBody
    @RequestMapping(value = "delArea", method = RequestMethod.POST)
    public void userareaDelete(@RequestParam(value = "uuid") String deleteUuid) {
        Map<String, String> params = new HashMap<>();
        areaService.delareaData(params,deleteUuid);
    }
    //---------------------------------------片区查询 和搜索功能

    @ResponseBody
    @RequestMapping("/oilData")
    public String getAreaDate(HttpServletRequest request) throws IOException {
        String url = "http://" + "49.233.36.65:8080" + "/ExtractLoadV01/";
        HttpMethod method = HttpMethod.GET;
        Map<String, String> params = new HashMap<>();
        params.put("oper", "?");
        params.put("limit", request.getParameter("limit").trim());
        params.put("page", request.getParameter("page").trim());

        return "{\"msg\":\"OK\",\"code\":\"0\",\"data\":[{\"1\":\"60514942\",\"2\":\"-10号车用柴油(Ⅵ)\",\"3\":\"-10号车用柴油(Ⅵ)\"}," +
                "{\"1\":\"60514943\",\"2\":\"0号车用柴油(Ⅵ)\",\"3\":\"0号车用柴油(Ⅵ)\"}," +
                "{\"1\":\"60514946\",\"2\":\"-20号车用柴油(Ⅵ)\",\"3\":\"-20号车用柴油(Ⅵ)\"}," +
                "{\"1\":\"60522773\",\"2\":\"92号车用乙醇汽油（E10)（VIA）\",\"3\":\"92号乙醇汽油（VIA）\"}," +
                "{\"1\":\"60522972\",\"2\":\"95号车用乙醇汽油（E10)（VIA）\",\"3\":\"95号乙醇汽油（VIA）\"}," +
                "{\"1\":\"60523121\",\"2\":\"98号车用乙醇汽油（E10)（VIA）/(VIB)\",\"3\":\"98号乙醇汽油（VIA）/(VIB)\"}" +
                "],\"count\":6}";
 /*       String areaName=request.getParameter("areaName");
        if(areaName==null||areaName.isEmpty()){
            return httpService.client(url, method, params);
        }
        else{
            params.put("areaname",areaName);
            return httpService.client(url, method, params);
        }*/
    }

    //----------------------------------------站点增加
    @RequestMapping(value = "addStation", method = RequestMethod.GET)
    public String stationAdd(Model model) {
        model.addAttribute("list", new Station());
        //?改成相对路径错误
        return "station/station_add";
    }

    @RequestMapping(value = "addStation", method = RequestMethod.POST)
    public String stationAdd(@ModelAttribute Station station) {
        Map<String, String> params = new HashMap<>();

        areaService.addstationData(params,station);
        //必须redirect,否则error page
        return "redirect:/stationmanagement/station";
    }
    //-------------------------------------------删除站点
    @ResponseBody
    @RequestMapping(value = "delStation", method = RequestMethod.POST)
    public void userstationDelete(@RequestParam(value = "uuid") String deleteUuid) {
        Map<String, String> params = new HashMap<>();
        areaService.delstationData(params,deleteUuid);
    }
    //---------------------------------------站点查询 和搜索功能

    @ResponseBody
    @RequestMapping("/stationData")
    public String getStationDate(HttpServletRequest request) throws IOException {
        String url = "http://" + "49.233.36.65:8080" + "/ExtractLoadV01/";
        HttpMethod method = HttpMethod.GET;
        Map<String, String> params = new HashMap<>();
        params.put("oper", "?");
        params.put("limit", request.getParameter("limit").trim());
        params.put("page", request.getParameter("page").trim());

        return "{\"msg\":\"OK\",\"code\":\"0\",\"data\":[{\"stationid\":\"32600624\",\"station_manage_id\":\"32600537\",\"stationname\":\"城南河西区天塔加油站\",\"station_short_name\":\"天塔加油站\",\"belongtocompany\":\"城南片区\",\"address\":\"天塔南500m\",\"phone\":\"1234567\",\"uuid\":\"sdfa651f6s5d1fsadf4w6f5\"}," +
                "{\"stationid\":\"32600681\",\"station_manage_id\":\"32600515\",\"stationname\":\"城南河西区珠江道加油站\",\"station_short_name\":\"珠江道加油站\",\"belongtocompany\":\"城南片区\",\"address\":\"珠江道附近\",\"phone\":\"7537532\",\"uuid\":\"sdfa65557375adf4w6f5\"}," +
                "{\"stationid\":\"32600683\",\"station_manage_id\":\"32600140\",\"stationname\":\"城南河西区宾西路加油站\",\"station_short_name\":\"宾西路加油站\",\"belongtocompany\":\"城南片区\",\"address\":\"宾西路附近\",\"phone\":\"4275335\",\"uuid\":\"sffjsdfgd1fsadf4w6f5\"}," +
                "{\"stationid\":\"32600763\",\"station_manage_id\":\"32600147\",\"stationname\":\"城南河西区玛钢路加油站\",\"station_short_name\":\"玛钢路加油站\",\"belongtocompany\":\"城南片区\",\"address\":\"玛钢路附近\",\"phone\":\"4242427\",\"uuid\":\"sdfgdfsgdfsgrgr\"}],\"count\":4}";
 /*       String stationName=request.getParameter("stationName");
        if(stationName==null||stationName.isEmpty()){
            return httpService.client(url, method, params);
        }
        else{
            params.put("stationname",stationName);
            return httpService.client(url, method, params);
        }*/
    }
}



