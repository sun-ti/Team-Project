package com.model;

import java.io.File;

import com.models.Utils_Init;

import net.sf.json.JSONObject;

public class Util_Init extends Util implements Utils_Init{
	//	初始化的网络对象类型;
	private String folder=null;
	public  static Util_Init instance;
	private String HTTP_IP=null,HTTP_PORT=null,DB_NAME=null,DB_PORT=null,DB_USERNAME=null,DB_PASSWORD=null,DB_ENCODING=null;
	
	public Util_Init() {
		
	}
	
	//	构造函数-初始化参数的控件内容;
	public Util_Init(Util_Net util_Net) {
		//	检测当前路径的状态;
		//	文件夹的路径的信息
		String propath	 =	util_Net.getPropath();
		this.folder  	 =	propath+File.separator+FOLDER1;
		
		//	进行文件夹的检测;
		super.checkFile(this.folder);
		//	检测当前的文件内容;
		checkConfiger();

	}
	
	public String getHTTP_IP() {
		return HTTP_IP;
	}

	public void setHTTP_IP(String hTTP_IP) {
		HTTP_IP = hTTP_IP;
	}

	public String getHTTP_PORT() {
		return HTTP_PORT;
	}

	public void setHTTP_PORT(String hTTP_PORT) {
		HTTP_PORT = hTTP_PORT;
	}

	public String getDB_NAME() {
		return DB_NAME;
	}

	public void setDB_NAME(String dB_NAME) {
		DB_NAME = dB_NAME;
	}

	public String getDB_PORT() {
		return DB_PORT;
	}

	public void setDB_PORT(String dB_PORT) {
		DB_PORT = dB_PORT;
	}

	public String getDB_USERNAME() {
		return DB_USERNAME;
	}

	public void setDB_USERNAME(String dB_USERNAME) {
		DB_USERNAME = dB_USERNAME;
	}

	public String getDB_PASSWORD() {
		return DB_PASSWORD;
	}

	public void setDB_PASSWORD(String dB_PASSWORD) {
		DB_PASSWORD = dB_PASSWORD;
	}

	public String getDB_ENCODING() {
		return DB_ENCODING;
	}

	public void setDB_ENCODING(String dB_ENCODING) {
		DB_ENCODING = dB_ENCODING;
	}

	public void checkConfiger() {
		//	文件路径的拼写;
		String filepath	=	this.folder+File.separator+FOLDER1+".txt";
		//	对配置文件进行相应的数据编写;
		File   file		=	new File(filepath);
		String config	=	readFile(file, UTF8);
		//	判断这些数据是否具有相应内容;
		//	判断配置文件是否为空,当为空时进行相应的数据处理;
		if(config!=null&&!config.trim().equals("")) {
			//	进行结果的显示;
			JSONObject object=JSONObject.fromObject(config);
//			System.out.println(object.toString());
			//	进行参数的初始化设置;
			setHTTP_IP(object.getString("HTTP-IP"));
			setHTTP_PORT(object.getString("HTTP-PORT"));
			setDB_NAME(object.getString("DB-NAME"));
			setDB_PORT(object.getString("DB-PORT"));
			setDB_USERNAME(object.getString("DB-USERNAME"));
			setDB_PASSWORD(object.getString("DB-PASSWORD"));
			setDB_ENCODING(object.getString("DB-ENCODING"));
		}
		//	否则初始化相应的参数信息文件;
		else {
			JSONObject object=new JSONObject();
			//	初始化的参数包括：1.http的IP地址;2.http的端口号;3.数据库的名称;4.数据库的端口号;5.数据库的用户名称;6.数据库的用户密码;7.数据库的字符集;
			object.put("HTTP-IP", "localhost");
			object.put("HTTP-PORT", "");
			object.put("DB-NAME", "sinopec");
			object.put("DB-PORT", "3306");
			object.put("DB-USERNAME", "root");
			object.put("DB-PASSWORD", "root");
			object.put("DB-ENCODING", "utf-8");
			writeFile(file, object.toString(), UTF8);
		}
		
		
	}

}
