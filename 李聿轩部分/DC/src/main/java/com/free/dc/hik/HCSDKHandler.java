package com.free.dc.hik;
import com.alibaba.fastjson.JSONObject;
import com.free.dc.bean.DeviceInterfaceException;
import com.free.dc.hik.alarm.AlarmChan;
import com.free.dc.model.DeviceInfo;
import com.sun.jna.Pointer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HCSDKHandler {

    private static HCNetSDK  HCNSDK = HCNetSDK.INSTANCE;

    HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();//设备登录信息
    HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();//设备信息

    private static AlarmChan alarmChan = null;

    public static Map<String, DeviceInfo> loginDevices = new HashMap<String,DeviceInfo>();

    public static List<JSONObject> alarmMsg = new ArrayList<JSONObject>();

    /**
     * 初始化视频SDK
     * @return
     */
    public static boolean sdkInit() throws DeviceInterfaceException{
        boolean iRet = HCNSDK.NET_DVR_Init();
        if(iRet==false){
            System.out.println("初始化失败！错误码："+HCNSDK.NET_DVR_GetLastError());
            throw new DeviceInterfaceException("初始化失败！错误码："+HCNSDK.NET_DVR_GetLastError());
        }
        return iRet;
    }

    /**
     * 设置回调函数
     * @throws DeviceInterfaceException
     */
    public static void setCallBackFunction() throws DeviceInterfaceException{
        if (alarmChan == null) {
            alarmChan = new AlarmChan();//报警回调函数实现
            Pointer pUser = null;
            if (!HCNSDK.NET_DVR_SetDVRMessageCallBack_V31(alarmChan, pUser)) {
                throw new DeviceInterfaceException("设置回调函数失败!");
            }
        }
    }

    /**
     * 用户注册
     * @param deviceInfo
     * @return
     */
    public static int deviceLogin(DeviceInfo deviceInfo) throws DeviceInterfaceException {
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();//设备登录信息
        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();//设备信息
        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(deviceInfo.getDeviceIP().getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, deviceInfo.getDeviceIP().length());
        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(deviceInfo.getUserName().getBytes(), 0, m_strLoginInfo.sUserName, 0, deviceInfo.getUserName().length());
        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(deviceInfo.getPassWord().getBytes(), 0, m_strLoginInfo.sPassword, 0, deviceInfo.getPassWord().length());
        m_strLoginInfo.wPort = deviceInfo.getPort();
        m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是
        m_strLoginInfo.write();
        int lUserID = HCNSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);
        if (lUserID==-1) {
            throw new DeviceInterfaceException("登陆失败，错误码："+HCNSDK.NET_DVR_GetLastError());
        }
        return lUserID ;
    }


    /**
     * 建立报警上传通道
     * @param deviceInfo
     * @return
     */
    public static boolean setupAlarmChan(DeviceInfo deviceInfo) throws DeviceInterfaceException{
        if(deviceInfo.getLUserID()<=0){
            throw new DeviceInterfaceException("此设备未登录！");
        }
        if (alarmChan == null) {
            alarmChan = new AlarmChan();//报警回调函数实现
            Pointer pUser = null;
            if (!HCNSDK.NET_DVR_SetDVRMessageCallBack_V31(alarmChan, pUser)) {
                throw new DeviceInterfaceException("设置回调函数失败!");
            }
        }
        switch(deviceInfo.getDeviceType()){
            case 1: //  车牌模块
                break;
            case 2: // 客流模块
                break;
            case 3: // 人脸模块
                break;
            case 4: // 异常行为模块
                break;
            default: // 无需配置
                break;
        }
        HCNetSDK.NET_DVR_SETUPALARM_PARAM m_strAlarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
        m_strAlarmInfo.dwSize=m_strAlarmInfo.size();
        m_strAlarmInfo.byLevel=1;//智能交通布防优先级：0- 一等级（高），1- 二等级（中），2- 三等级（低）
        m_strAlarmInfo.byAlarmInfoType=1;//智能交通报警信息上传类型：0- 老报警信息（NET_DVR_PLATE_RESULT），1- 新报警信息(NET_ITS_PLATE_RESULT)
        m_strAlarmInfo.byDeployType =1; //布防类型(仅针对门禁主机、人证设备)：0-客户端布防(会断网续传)，1-实时布防(只上传实时数据)
        m_strAlarmInfo.write();
        int lAlarmHandle = HCNSDK.NET_DVR_SetupAlarmChan_V41(deviceInfo.getLUserID(), m_strAlarmInfo);
        if(lAlarmHandle==-1){
            throw new DeviceInterfaceException("建立报警上传通道失败！错误码："+HCNSDK.NET_DVR_GetLastError());
        }
        return true;
    }

    /**
     * 配置网络设备
     * @param lUserID
     * @param dwCommand
     * @return
     */
    public static boolean setDVCConfig(int lUserID,int dwCommand){
        //HCNetSDK.NET_DVR_SetDVRConfig(lUserID,dwCommand);
        return true;
    }

    /**
     * 退出设备
     * @param lUserID
     * @return
     */
    public static boolean deviceLogout(int lUserID){
        boolean iRet = HCNSDK.NET_DVR_Logout(lUserID);
        return iRet;
    }
}

