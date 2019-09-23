package com.sinopec.springbootdemo.service;

import com.sinopec.springbootdemo.dao.MonitorinfoDao;
import com.sinopec.springbootdemo.entity.Monitorinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorinfoService {

    @Autowired
    private MonitorinfoDao monitorinfoDao;

    public List<Monitorinfo> getAllHumanFaceInfoPage(int limit, int page) {
        return monitorinfoDao.queryAllMonitorinfoPage(4370, limit, page);
    }

    public int getHumanFaceInfoCount() {
        return monitorinfoDao.getInfoCount(4370);
    }

    public List<Monitorinfo> getAllCarPlateInfoPage(int limit, int page) {
        return monitorinfoDao.queryAllMonitorinfoPage(12368, limit, page);
    }

    public int getCarPlateInfoCount() {
        return monitorinfoDao.getInfoCount(12368);
    }

    public List<Monitorinfo> getAllHumanFlowInfoPage(int limit, int page) {
        return monitorinfoDao.queryAllMonitorinfoPage(4355, limit, page);
    }

    public int getHumanFlowInfoCount() {
        return monitorinfoDao.getInfoCount(4355);
    }
}
