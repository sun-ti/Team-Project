package com.free.dc.controller;

import com.alibaba.fastjson.JSONObject;
import com.free.dc.bean.DeviceInterfaceException;
import com.free.dc.hik.HCSDKHandler;
import com.free.dc.hik.alarm.AlarmChan;
import com.free.dc.model.DeviceInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

/**
 * 首页初始化类
 */
@Controller
public class MainController {

    /**
     * 首页
     * @param model
     * @return
     */
    @RequestMapping({"","/","hktest"})
    public String init(Model model){

        String msg = "设备初始化并设置回调函数成功";
        // 设备初始化
        try{
            HCSDKHandler.sdkInit();
        }catch(DeviceInterfaceException ex){
            msg = ex.getMessage();
            System.out.println(msg);
        }
        try{
            HCSDKHandler.setCallBackFunction();
        }catch(Exception ex){
            msg = ex.getMessage();
        }
        model.addAttribute("msg",msg);
        return "hik";
    };

    /**
     * 登陆并连接设备
     * @param deviceInfo 设备信息
     * @return
     * @throws Exception
     */
    @RequestMapping({"hkac"})
    @ResponseBody
    JSONObject deviceConnect(DeviceInfo deviceInfo) throws Exception {
        JSONObject retJo = new JSONObject();
        int lUserID = -1;
        // 登陆
        try{
            lUserID = HCSDKHandler.deviceLogin(deviceInfo);
        }catch(DeviceInterfaceException ex){
            retJo.put("msg",ex.getMessage());
            return retJo;
        }
        // 保存登陆设备信息及登陆句柄
        deviceInfo.setLUserID(lUserID);
        HCSDKHandler.loginDevices.put(deviceInfo.getDeviceIP(),deviceInfo);
        // 建立报警上传通道
        try{
            HCSDKHandler.setupAlarmChan(deviceInfo);
        }catch(DeviceInterfaceException ex){
            retJo.put("msg",ex.getMessage());
            return retJo;
        }
        retJo.put("suc","已成功建立报警上传通道！");
        return retJo;
    }
    @RequestMapping({"hkacl"})
    @ResponseBody JSONObject hkacl(DeviceInfo deviceInfo)  {
        JSONObject jo = new JSONObject();
        // 退出用户登陆
        if(HCSDKHandler.loginDevices.containsKey(deviceInfo.getDeviceIP())){
            DeviceInfo di = HCSDKHandler.loginDevices.get(deviceInfo.getDeviceIP());
            if(di!=null&&di.getLUserID()!=null){
                HCSDKHandler.deviceLogout(di.getLUserID());
                jo.put("msg","已退出设备");
            }else{
                jo.put("msg","该设备并未登陆");
            }
        }else{
            jo.put("suc","该地址未注册");
        }
        return jo;
    }

    @RequestMapping({"hkcp"})
    @ResponseBody JSONObject hkcp() {
        JSONObject jo = new JSONObject();
        if(HCSDKHandler.alarmMsg!=null&&HCSDKHandler.alarmMsg.size()>0){
            JSONObject alarmJSON = HCSDKHandler.alarmMsg.get(0);
            String pic1 = alarmJSON.getString("pic1");
            String pic2 = alarmJSON.getString("pic2");
            if(pic1!=null){
                pic1.replace(AlarmChan.outputFilePath+ File.separator,"");
            }
            if(pic2!=null){
                pic2.replace(AlarmChan.outputFilePath+ File.separator,"");
            }
            jo.put("pic",pic1);
            jo.put("cp", alarmJSON.getString("deviceIP")+":"+alarmJSON.getString("errMsg"));
            jo.put("cppic", pic2);
            System.out.println(alarmJSON.toString());
            HCSDKHandler.alarmMsg.remove(alarmJSON);
        }
        return jo;
    }
}