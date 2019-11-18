package com.free.dc.model;

/**
 * 设备信息
 */
public class DeviceInfo {
    // 用户句柄
    private Integer lUserID;
    // 设备IP
    private String deviceIP;
    // 设备端口号
    private short port;
    // 设备用户名
    private String userName;
    // 设备密码
    private String passWord;
    // 设备类型 1：车牌识别，2：客流统计，3：人脸识别，4：异常行为分析
    private int deviceType;

    public DeviceInfo(){};

    public DeviceInfo(String deviceIP, short port, String userName, String passWord, int deviceType){
        this.deviceIP = deviceIP;
        this.port = port;
        this.userName = userName;
        this.passWord = passWord;
        this.deviceType = deviceType;
    }

    public Integer getLUserID() {
        return lUserID;
    }

    public void setLUserID(Integer lUserID) {
        this.lUserID = lUserID;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public short getPort() {
        return port;
    }

    public void setPort(short port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
}
