package com.free.dc.hik.alarm;

import com.free.dc.MainApplication;
import com.free.dc.hik.HCNetSDK;
import com.free.dc.tool.DBaseUtils;
import com.free.dc.tool.Util;
import com.free.dc.tool.Utils;
import com.sun.jna.Pointer;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  接收摄像头数据
 */
public class AlarmChan implements HCNetSDK.FMSGCallBack_V31{
    //  进行输出的路径信息内容;
    public static String outputFilePath     = Util.UPLOAD;

    @Override
    public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        AlarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
        return true;
    }
    //  获得车辆的具体信息;
    private String[] getCarInfo(String str){
        //  获得相应的车辆的数据组;
        String [] strs= new String[2];
        //  获得车牌的颜色;
        strs[0]       = str.substring(0,"蓝".length());
        //  获得车牌的号码;
        strs[1]       = str.substring("蓝".length(),str.length());
        return strs;
    }

    /**
     * 接受设备采集信息
     * @param lCommand  命令
     * @param pAlarmer  类型
     * @param pAlarmInfo
     * @param dwBufLen
     * @param pUser
     */
    public void AlarmDataHandle(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {

        System.out.println("警报类型 ["+ lCommand+"]");

        //  进行时间的设置;
        MainApplication.dBaseUtils.getCurrentTime();
        //  报警信息
        StringBuilder   alarmMsg  = new StringBuilder();
        //  IP地址的分析;
        byte[]          deviceips = pAlarmer.sDeviceIP;
        //  获得设备的ip地址;
        String          deviceip  = new String(deviceips).trim();
        //  对相应的数据信息进行处理;
        //  根据摄像头ip获得相应的站点的id编号;
        String[]        results   = getStationId(MainApplication.dBaseUtils,deviceip);

        String  shexiangtou_kind  = results[0];
        //  判断摄像头类别的标志;
        int             kind      = Integer.valueOf(shexiangtou_kind);
        String          stationid = results[1];

        //  进行相应的数据处理;
        //  声明数据库操作;
        String          pic1      = null;
        String          pic1file  = null;
        String          pic2      = null;
        String          pic2file  = null;

        String          color     = "无";
        String          plate     = "无";
        String          cartype   = "无";

        //  SQL-语句;
        String          sql       = "";

        //  对存储的图像路径进行stationid的划分;
        String          folder    = outputFilePath+File.separator+stationid+File.separator+deviceip;

        //  获得当前的时间内容;
        MainApplication.dBaseUtils.getCurrentTime();
        //  获得当前时间的字符;
        String          datatime  = MainApplication.dBaseUtils.tcurrent;
        //  获得当前时间长整型;
        long            datatime1 = MainApplication.dBaseUtils.tcurrentlong;

        //  获得当前时间的标签;
        String          getNewTime= MainApplication.dBaseUtils.tnew;

        //  进行文件夹的检测;
        MainApplication.dBaseUtils.checkFile(folder);

        try {
            //lCommand报警类型
            switch (lCommand){
                // 交通抓拍结果上传
                case HCNetSDK.COMM_UPLOAD_PLATE_RESULT:

                    HCNetSDK.NET_DVR_PLATE_RESULT strPlateResult = new HCNetSDK.NET_DVR_PLATE_RESULT();
                    strPlateResult.write();
                    Pointer pPlateInfo  = strPlateResult.getPointer();
                    pPlateInfo.write(0, pAlarmInfo.getByteArray(0, strPlateResult.size()), 0, strPlateResult.size());
                    strPlateResult.read();

                    try {
                        String  srt3    =   new String(strPlateResult.struPlateInfo.sLicense,"GBK").trim();
                        if(srt3!=null){
                            alarmMsg.append("status:OK,");
                            if(!srt3.contains("无车牌")){
                                //  车辆颜色;
                                color   =   getCarInfo(srt3)[0];
                                //  车牌编号;
                                plate   =   getCarInfo(srt3)[1];
                                //  车辆内容;
                                srt3    =   "color:"+color+",num:"+plate;
                            }
                            else{
                                srt3="color:NONE,num:NONE";
                            }
                        }else{
                            alarmMsg.append("status:NO,");
                            srt3    =   "color:NONE,num:NONE";
                        }
                        //  添加车牌内容;
                        alarmMsg.append(srt3);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    //  判断摄像头的类型-决定图像是否上传;
                    if(kind!=Util.KIND_5){
                        if(strPlateResult.dwPicLen>0){
                            FileOutputStream fout    = null;
                            try {
                                pic1     = getNewTime+"_plateResult.jpg";
                                pic1file = folder+File.separator+getNewTime+"_plateResult.jpg";
                                pic2     = null;
                                fout     = new FileOutputStream(pic1file);

                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers  = strPlateResult.pBuffer1.getByteBuffer(offset, strPlateResult.dwPicLen);
                                byte [] bytes       = new byte[strPlateResult.dwPicLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.flush();
                                fout.close();
                            } catch (Exception e) {
                                // TODO 异常待处理
                                e.printStackTrace();
                            }finally {
                                try{
                                    if(fout!=null){
                                        fout.flush();
                                        fout.close();
                                    }
                                }catch(IOException ex){
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                    break;
                // 车牌识别模块
                case HCNetSDK.COMM_ITS_PLATE_RESULT:
                    HCNetSDK.NET_ITS_PLATE_RESULT strItsPlateResult = new HCNetSDK.NET_ITS_PLATE_RESULT();
                    strItsPlateResult.write();
                    Pointer pItsPlateInfo = strItsPlateResult.getPointer();
                    pItsPlateInfo.write(0, pAlarmInfo.getByteArray(0, strItsPlateResult.size()), 0, strItsPlateResult.size());
                    strItsPlateResult.read();
                    try {
                        String srt3 =   new String(strItsPlateResult.struPlateInfo.sLicense,"GBK").trim();
//                        System.out.println(srt3);
                        if(srt3!=null){
                            alarmMsg.append("status:OK,");
                            if(!srt3.contains("无车牌")){
                                color   = getCarInfo(srt3)[0];
                                plate   = getCarInfo(srt3)[1];
                                cartype = strItsPlateResult.byVehicleType+"";
                                srt3    = "type:"+cartype+",color:"+color+",num:"+plate;

                            }else
                                srt3    = "type:"+strItsPlateResult.byVehicleType+",color:NONE,num:NONE";
                        }else{
                            alarmMsg.append("status:NO,");
                            srt3    =   "type:"+strItsPlateResult.byVehicleType+",color:NONE,num:NONE";
                        }
                        alarmMsg.append(srt3);
                    }
                    catch (Exception e1) {
                        //TODO 异常待处理
                        e1.printStackTrace();
                    }
                    //  判断摄像头的类型-决定图像是否上传;
                    if(kind!= Util.KIND_5){
                        for(int i=0;i<strItsPlateResult.dwPicNum;i++){

                            if(strItsPlateResult.struPicInfo[i].dwDataLen>0){
                                FileOutputStream fout = null;
                                try {

                                    pic1     = getNewTime+"_type_"+strItsPlateResult.struPicInfo[i].byType+"_ItsPlate.jpg";
                                    pic1file = folder+File.separator+getNewTime+"_type_"+strItsPlateResult.struPicInfo[i].byType+"_ItsPlate.jpg";
                                    pic2     = null;
                                    fout     = new FileOutputStream(pic1file);

                                    //将字节写入文件
                                    long offset = 0;
                                    ByteBuffer buffers = strItsPlateResult.struPicInfo[i].pBuffer.getByteBuffer(offset, strItsPlateResult.struPicInfo[i].dwDataLen);
                                    byte [] bytes = new byte[strItsPlateResult.struPicInfo[i].dwDataLen];
                                    buffers.rewind();
                                    buffers.get(bytes);
                                    fout.write(bytes);
                                    fout.flush();
                                    fout.close();
                                } catch (Exception e) {
                                    // TODO 异常待处理
                                    e.printStackTrace();
                                } finally {
                                    try{
                                        if(fout!=null){
                                            fout.flush();
                                            fout.close();
                                        }
                                    }catch(IOException ex){
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    break;
                //客流量统计报警信息
                case HCNetSDK.COMM_ALARM_PDC:
                    HCNetSDK.NET_DVR_PDC_ALRAM_INFO strPDCResult = new HCNetSDK.NET_DVR_PDC_ALRAM_INFO();
                    strPDCResult.write();
                    Pointer pPDCInfo = strPDCResult.getPointer();
                    pPDCInfo.write(0, pAlarmInfo.getByteArray(0, strPDCResult.size()), 0, strPDCResult.size());
                    strPDCResult.read();
                    alarmMsg.append("客流量统计");
                    if(strPDCResult.byMode == 0) { // 实时统计人数
                        strPDCResult.uStatModeParam.setType(HCNetSDK.NET_DVR_STATFRAME.class);
                        alarmMsg.append(",in:").append(strPDCResult.dwEnterNum).append(",out:").append(strPDCResult.dwLeaveNum)
                                .append(",byMode:").append(strPDCResult.byMode).append(",dwRelativeTime:").append(strPDCResult.uStatModeParam.struStatFrame.dwRelativeTime)
                                .append(",dwAbsTime:").append(strPDCResult.uStatModeParam.struStatFrame.dwAbsTime);
                    }else if(strPDCResult.byMode == 1) {  // 时段统计
                        strPDCResult.uStatModeParam.setType(HCNetSDK.NET_DVR_STATTIME.class);
                        String strtmStart = "" + String.format("%04d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwYear) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwMonth) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwDay) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwHour) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwMinute) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwSecond);
                        String strtmEnd = "" + String.format("%04d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwYear) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwMonth) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwDay) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwHour) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwMinute) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwSecond);
                        alarmMsg.append(",in:").append(strPDCResult.dwEnterNum).append(",out:").append(strPDCResult.dwLeaveNum)
                                .append(",byMode:").append(strPDCResult.byMode).append(",tmStart:").append(strtmStart)
                                .append(",tmEnd:").append(strtmEnd);
                    }
                    pic1 = null;
                    pic2 = null;
                    break;
                case HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT: //实时人脸抓拍上传
                    HCNetSDK.NET_VCA_FACESNAP_RESULT strFaceSnapInfo = new HCNetSDK.NET_VCA_FACESNAP_RESULT();
                    strFaceSnapInfo.write();
                    Pointer pFaceSnapInfo = strFaceSnapInfo.getPointer();
                    pFaceSnapInfo.write(0, pAlarmInfo.getByteArray(0, strFaceSnapInfo.size()), 0, strFaceSnapInfo.size());
                    strFaceSnapInfo.read();
                    alarmMsg.append("人脸识别");
                    alarmMsg.append(",level:").append(strFaceSnapInfo.dwFaceScore)
                            .append(",age:").append(strFaceSnapInfo.struFeature.byAgeGroup)
                            .append(",sex:").append(strFaceSnapInfo.struFeature.bySex);

                    //人脸图片写文件
                    FileOutputStream small = null;
                    FileOutputStream big   = null;
                    try {

                        pic1      = getNewTime+"_small.jpg";
                        pic1file  = folder+File.separator+getNewTime+"_small.jpg";

                        pic2      = getNewTime+"_big.jpg";
                        pic2file  = folder+File.separator+getNewTime+"_big.jpg";

                        small = new FileOutputStream(pic1file);
                        big   = new FileOutputStream(pic2file);

                        //  小图上传;
                        if(strFaceSnapInfo.dwFacePicLen > 0){

                            //  将小图进行上传;
                            try {
                                small.write(strFaceSnapInfo.pBuffer1.getByteArray(0, strFaceSnapInfo.dwFacePicLen), 0, strFaceSnapInfo.dwFacePicLen);
                                small.close();
                            } catch (IOException ex) {
                                Logger.getLogger(AlarmChan.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        //  大图上传;
                        if(strFaceSnapInfo.dwFacePicLen > 0){

                            //  将大图进行上传;
                            try {
                                big.write(strFaceSnapInfo.pBuffer2.getByteArray(0, strFaceSnapInfo.dwBackgroundPicLen), 0, strFaceSnapInfo.dwBackgroundPicLen);
                                big.close();
                            } catch (IOException ex) {
                                Logger.getLogger(AlarmChan.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(AlarmChan.class.getName()).log(Level.SEVERE, null, ex);
                    }finally {
                        try{
                            if(small!=null){
                                small.flush();
                                small.close();
                            }
                            if(big!=null){
                                big.flush();
                                big.close();
                            }
                        }catch(IOException ex){
                            ex.printStackTrace();
                        }
                    }
                    break;
                case HCNetSDK.COMM_ALARM_RULE: // 行为分析信息上传
                    HCNetSDK.NET_VCA_RULE_ALARM strVcaAlarm = new HCNetSDK.NET_VCA_RULE_ALARM();
                    strVcaAlarm.write();
                    Pointer pVcaInfo = strVcaAlarm.getPointer();
                    pVcaInfo.write(0, pAlarmInfo.getByteArray(0, strVcaAlarm.size()), 0, strVcaAlarm.size());
                    strVcaAlarm.read();

                    switch (strVcaAlarm.struRuleInfo.wEventTypeEx){
                        case 1:
                            alarmMsg.append("穿越警戒面")
                                    .append(",_wPort:").append(strVcaAlarm.struDevInfo.wPort)
                                    .append(",_byChannel:").append(strVcaAlarm.struDevInfo.byChannel)
                                    .append(",_byIvmsChannel:").append(strVcaAlarm.struDevInfo.byIvmsChannel)
                                    .append(",_Dev IP:").append(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                            break;
                        case 2:
                            alarmMsg.append("目标进入区域")
                                    .append(",_wPort:").append(strVcaAlarm.struDevInfo.wPort)
                                    .append(",_byChannel:").append(strVcaAlarm.struDevInfo.byChannel)
                                    .append(",_byIvmsChannel:").append(strVcaAlarm.struDevInfo.byIvmsChannel)
                                    .append(",_Dev IP:").append(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                            break;
                        case 3:
                            alarmMsg.append("目标离开区域")
                                    .append(",_wPort:").append(strVcaAlarm.struDevInfo.wPort)
                                    .append(",_byChannel:").append(strVcaAlarm.struDevInfo.byChannel)
                                    .append(",_byIvmsChannel:").append(strVcaAlarm.struDevInfo.byIvmsChannel)
                                    .append(",_Dev IP:").append(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                            break;
                        default:
                            alarmMsg.append("其他行为分析报警，事件类型：").append(strVcaAlarm.struRuleInfo.wEventTypeEx )
                                    .append(",_wPort:").append(strVcaAlarm.struDevInfo.wPort)
                                    .append(",_byChannel:").append(strVcaAlarm.struDevInfo.byChannel)
                                    .append(",_byIvmsChannel:").append(strVcaAlarm.struDevInfo.byIvmsChannel)
                                    .append(",_Dev IP:").append(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                            break;
                    }

                    if(strVcaAlarm.dwPicDataLen>0){
                        FileOutputStream fout = null;
                        try {
                            pic1    = "wEventTypeEx_"+strVcaAlarm.struRuleInfo.wEventTypeEx+"_"+getNewTime+"_vca.jpg";
                            pic1file= folder+File.separator+"wEventTypeEx_"+strVcaAlarm.struRuleInfo.wEventTypeEx+"_"+getNewTime+"_vca.jpg";

                            fout    = new FileOutputStream(pic1file);
                            pic2    = null;
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strVcaAlarm.pImage.getByteBuffer(offset, strVcaAlarm.dwPicDataLen);
                            byte [] bytes = new byte[strVcaAlarm.dwPicDataLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.flush();
                            fout.close();
                        }catch (Exception e) {
                            // TODO 异常待处理
                            e.printStackTrace();
                        } finally {
                            try{
                                if(fout!=null){
                                    fout.flush();
                                    fout.close();
                                }
                            }catch(IOException ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                    break;
                default:

                    break;
            }
            ////////////////////////////////////////////////////////////////////////////////////

            //  进行图像内容的标识别;
            if(pic1==null){
                pic1="无图像保存";
            }
            if(pic2==null){
                pic2="无图像保存";
            }

            //  进行表单的检测;
            String  tablename  =  checkTableExist(MainApplication.dBaseUtils, DBaseUtils.DB_name,stationid);

            //  在此表的基础上
            sql                =  "insert into "+tablename+" (jiankong_id,lcommand,deviceip,err_msg,color,plate,car_type,datetime,pic1,pic2,state) values " +
                                  "("+datatime1+","+lCommand+",'"+deviceip+"','"+alarmMsg.toString().trim()+"','"+color+"','"+plate+"','"+cartype+"','"+datatime+"','"+pic1+"','"+pic2+"',0)";

            //  将进行数据库的操作进行更新;
            MainApplication.dBaseUtils.update(sql);
            //  中间的时间间隔;
            Thread.sleep(3000);

            //  TODO前端数据的传输;
            // HCSDKHandler.alarmMsg.add(jo);
        } catch (Exception ex) {
            Logger.getLogger(AlarmChan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //  根据设备的ip地址查询出相应的stationid
    private String[] getStationId(DBaseUtils db, String deviceip){
        //  结果的标志位;
        String[]            results= new String[2];
        //  SQL语句的内容;
        String              sql    = "select a.kind,b.stationid from shebei_shexiangtou a,zhandian_shexiangtou b where a.shexiangtou_id=b.shexiangtou_id and  a.shexiangtou_ip='"+deviceip+"'";
        //  获得对应的数据结果;
        ArrayList<String[]> list   = db.select(sql);
        //  查询结果不为空;
        if(list.size()!=0){
            //  查询出的数据结果;
            String[]        items  = list.get(0);
            //  返回数据结果;
            results[0]             = items[0];
            results[1]             = items[1];
        }
        //  返回结果信息
        return results;
    }

    private String checkTableExist(DBaseUtils db,String dbname, String stationid){
        //  SQl语句;
        String              tablename= "tongji_"+stationid+"_jiankong";
        String              sql      = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+dbname+"' and TABLE_NAME='"+tablename+"'";
        //  查询结果的返回值;
        ArrayList<String[]> list     = db.select(sql);
        //  当表单不存在的情况下;
        if(list.size()==0){
            //  进行表单的创建;
            sql="create table "+tablename+" (" +
                "autoid bigint primary key auto_increment," +
                "jiankong_id bigint," +
                "lcommand bigint," +
                "deviceip varchar(20)," +
                "err_msg varchar(1000)," +
                "color varchar(20)," +
                "plate varchar(20)," +
                "car_type varchar(20)," +
                "datetime varchar(20)," +
                "pic1 varchar(200)," +
                "pic2 varchar(200)," +
                "state int)";
            //  进行表单的操作;
            db.update(sql);
            //  将表单列入到管理表中-从而方便统计;
            sql="insert into tongji_table_list (tablename,type) values('"+tablename+"','jiankong')";
            //  进行数据的更新;
            db.update(sql);
        }

        //  返回设置表的名称;
        return tablename;
    }
}
