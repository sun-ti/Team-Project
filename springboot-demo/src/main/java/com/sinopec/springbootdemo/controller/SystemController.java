package com.sinopec.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system")
public class SystemController {

    @RequestMapping("/camera_new")
    public String cameraNew() {
        return "camera/project_camera_new";
    }

    @RequestMapping("/interface")
    public String interfaceNew() {
        return "interface/project_interface";
    }

    @RequestMapping("/station")
    public String station() {
        return "station/project_station";
    }

    @RequestMapping("/oilMachine")
    public String oilMachine() {
        return "oil/project_oil";
    }

    @RequestMapping("/log")
    public String log() {
        return "log/project_log";
    }

    @RequestMapping("/client")
    public String client() {
        return "client/project_client";
    }
}
