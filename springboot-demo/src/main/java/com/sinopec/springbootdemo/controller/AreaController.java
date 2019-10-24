package com.sinopec.springbootdemo.controller;

import com.sinopec.springbootdemo.entity.Area;
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
@RequestMapping("/stationmanagement")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @Autowired
    private HttpService httpService;

    @RequestMapping("/area")
    public String area() {
        return "station/project_area";
    }

    @RequestMapping("/station")
    public String station() {
        return "station/project_station";
    }

    //----------------------------------------片区增加
    @RequestMapping(value = "addArea", method = RequestMethod.GET)
    public String areaAdd(Model model) {
        model.addAttribute("list", new Area());
        //?改成相对路径错误
        return "station/area_add";
    }

    @RequestMapping(value = "addArea", method = RequestMethod.POST)
    public String areaAdd(@ModelAttribute Area area) {
        Map<String, String> params = new HashMap<>();

        areaService.addareaData(params,area);
        //必须redirect,否则error page
        return "redirect:/stationmanagement/area";
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
    @RequestMapping("/areaData")
    public String getAreaDate(HttpServletRequest request) throws IOException {
        String url = "http://" + "49.233.36.65:8080" + "/ExtractLoadV01/";
        HttpMethod method = HttpMethod.GET;
        Map<String, String> params = new HashMap<>();
        params.put("oper", "?");
        params.put("limit", request.getParameter("limit").trim());
        params.put("page", request.getParameter("page").trim());

        return "{\"msg\":\"OK\",\"code\":\"0\",\"data\":[{\"areaname\":\"城南片区\",\"areaid\":\"32600118\",\"uuid\":\"sdfa651f6s5d1fsadf4w6f5\"},{\"areaname\":\"城西片区\",\"areaid\":\"32600117\",\"uuid\":\"sdfa6asdasdsada5d1fsadf4w6f5\"}],\"count\":2}";
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



