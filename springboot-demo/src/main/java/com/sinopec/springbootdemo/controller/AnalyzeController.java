package com.sinopec.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analyze")
public class AnalyzeController {

    @RequestMapping("/carflow")
    public String car() {
        return "analyze/carflow";
    }

    @RequestMapping("/passengerflow")
    public String passenger() {
        return "analyze/passengerflow";
    }

    @RequestMapping("/ruDianBi")
    public String rudianbi() {
        return "analyze/ruDianBi";
    }

    @RequestMapping("/costomer")
    public String costomer() {
        return "analyze/costomer";
    }

    @RequestMapping("/parking")
    public String parking() {
        return "analyze/parking";
    }
}
