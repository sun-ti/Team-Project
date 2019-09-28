package com.model;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.models.Utils_Init;

public class Util_Init extends Util implements Utils_Init{
	//	初始化的网络对象类型;
	private String folder=null;
	public  static Util_Init instance;
	
	//	构造函数-初始化参数的控件内容;
	public Util_Init(HttpServletRequest  request,HttpServletResponse response) {
		//	检测当前路径的状态;
		Util_Net util_Net=	new Util_Net(request, response);
		//	文件夹的路径的信息
		String propath	 =	util_Net.getPropath();
		this.folder  	 =	propath+File.separator+FOLDER1;
		
		//	进行文件夹的检测;
		super.checkFile(this.folder);
		
		//	将实例对象赋值给当前的内容;
		instance=this;
	}
	
	public void checkConfiger() {
		//	文件路径的拼写;
		String filepath	=	this.folder+File.separator+FOLDER1+".txt";
		//	检测文件的路径;
		checkFile(filepath);
		File   file		=	new File(filepath);
		String config	=	readFile(file, UTF8);
		//	判断这些数据是否具有相应内容;
		if(config.trim()!=null) {
			
		}
		
		
	}

}
