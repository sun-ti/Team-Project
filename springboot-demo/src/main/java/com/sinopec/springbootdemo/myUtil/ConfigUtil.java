package com.sinopec.springbootdemo.myUtil;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
public class ConfigUtil {

    private String mysqlAddr;

    public String getMysqlAddr() {
        return mysqlAddr;
    }

    public void setMysqlAddr(String addr) {
        this.mysqlAddr = addr;
    }
}
