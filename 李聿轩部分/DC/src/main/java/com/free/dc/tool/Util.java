package com.free.dc.tool;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//	进行处理的数据工具类名;
public class Util implements Utils{
	public static final String ROOT        = "E:"+File.separator+"dc";
	public static final String LIB   	   = ROOT+File.separator+"lib";
	public static final String UPLOAD	   = ROOT+File.separator+"upload";
	//	设置相应的摄像头的种类;
	//	车牌获取;
	public static final int KIND_1		   = 1;
	//	客流统计;
	public static final int KIND_2		   = 2;
	//	人脸识别;
	public static final int KIND_3		   = 3;
	//	卸油区域;
	public static final int KIND_4		   = 4;
	//	车流统计;
	public static final int KIND_5		   = 5;

	//	进行数据库的参数设置;
	public static final String DB_DRIVER   = "com.mysql.jdbc.Driver";
	//	private String driver = "com.mysql.cj.jdbc.Driver";

	public static final String DB_IP	   = "localhost";
	public static final String DB_port	   = "3306";
	public static final String DB_name	   = "sinopec";
	public static final String DB_charset  = "utf8";

	public static final String DB_url 	   = "jdbc:mysql://"+DB_IP+":"+DB_port+"/"+DB_name+"?useUnicode=true&characterEncoding="+DB_charset;

	//	private String url = "jdbc:mysql://localhost:3306/formaldb?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=UTC";
	public static final String DB_user 	   = "root";
	public static final String DB_password = "root";

	//	数据的标签信息内容;
	public String tag;
	//	时间的初始的长整型;
	private long t1;
	//	时间的结束的长整型;
	private long t2;
	//	当前时间的长整型;
	public long   tcurrentlong;
	//	当前时间的字符串;
	public String tcurrent;
	//	当前时间内容;
	public String tnew;

	//	构造函数的内容;
	public Util() {
		
	}
	//	获得当前项目的根目录;
	public static String getLibPath(){
		//	当前的文件的路径;
		String path= LIB;
		//	新生成的路径;
		Util   util= new Util();
		//	进行文件的检测;
		util.checkFile(path);
		return path;
	}


	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	//	重载文件监测的内容;
	public void checkFile(String path) {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}
	//	开始进行程序的相应的操作;
	public void tagStartModule() {
		System.out.println("'"+this.tag+"'功能-开始运行!");
		this.t1=System.currentTimeMillis();
	}
	
	//	结束进行程序的相应的操作;
	public void tagEndStartModule() {
		System.out.println("--------------------------------");
		System.out.println("'"+this.tag+"'功能-结束运行!");
		this.t2=System.currentTimeMillis();
	}
	
	//	程序花费时间的相应的操作;
	public void tagComputeProcessingTime() {
		long td=this.t2-this.t1;
		System.out.println("'"+this.tag+"'功能-花费时间:"+td+"毫秒!");
		System.out.println("--------------------------------");
	}
	//	设置网页的相应内容;
	public void setHttpServletParameter(HttpServletRequest request,HttpServletResponse response){
		//	输入的字符串编辑内容;
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	输出的相应的内容信息;
		response.setHeader("Context-Type","text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
	}
	//	获得系统获得UUID的方法;
	public String getUUID() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String getNewTime() {
		SimpleDateFormat sf      = new SimpleDateFormat("yyyyMMddHHmmss");
		String           newName = sf.format(new java.util.Date());
		return newName;
	}

	//	获得当前的时间内容;
	public void getCurrentTime(){
		//	获得当前时间的长整型数值;
		tcurrentlong			=	System.currentTimeMillis();
		//  时间字符串的安置;
		Date 			 date	=	new Date(tcurrentlong);
		SimpleDateFormat format = 	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//	获得当前时间的字符串数值;
		tcurrent				= 	format.format(date);

        SimpleDateFormat sf      = new SimpleDateFormat("yyyyMMddHHmmss");
        tnew				    = 	sf.format(date);
	}
}
