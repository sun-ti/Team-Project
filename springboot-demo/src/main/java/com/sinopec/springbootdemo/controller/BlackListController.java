package com.sinopec.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blacklist")
public class BlackListController {

    @RequestMapping("/blackpost")
    public String post() {
        return "blacklist/project_blackpost_new";
    }

    @RequestMapping("/record")
    public String record() {
        return "blacklist/project_record_new";
    }
}
