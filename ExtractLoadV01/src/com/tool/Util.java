package com.tool;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lenovo
 *
 */
/**
 * @author Lenovo
 *
 */
public class Util implements Utils{

	public static final String FOLDER="SYS"+File.separator+"SINOPEC";
	public static final String PATH = "D:"+File.separator+FOLDER+File.separator+"SYS_image";
	public static final String PATH2= "D:"+File.separator+FOLDER+File.separator+"SYS_download";
	public static final String PATH3= "D:"+File.separator+FOLDER+File.separator+"SYS_info";
	
	//	文件存储的标签;
	public String KEY_1="pic";
	public String TAG_1="%";
	public String TAG_2="\\";
	
	public String tag; 
	private long t1;
	private long t2;
	
	//	IP地址;
	private String ip;
	//	端口号;
	private int    port;
	//	项目名称;
	private String project;
	
	
	//	参数的内容;
	private HttpServletRequest request;
	
	
	//	构造函数的内容;
	public Util() {
		
	}
	public Util(HttpServletRequest request) {
		this.request=request;
		getHttpUrl();
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
//		//
//		/* 允许跨域的主机地址 */
//		response.setHeader("Access-Control-Allow-Origin", "*");  
//		/* 允许跨域的请求方法GET, POST, HEAD 等 */
//		response.setHeader("Access-Control-Allow-Methods", "*");  
//		/* 重新预检验跨域的缓存时间 (s) */
//		response.setHeader("Access-Control-Max-Age", "3600");  
//		/* 允许跨域的请求头 */
//		response.setHeader("Access-Control-Allow-Headers", "*");  
//		/* 是否携带cookie */
//		response.setHeader("Access-Control-Allow-Credentials", "true");  
		
	}
	//	获得系统获得UUID的方法;
	public String getUUID() {
		return UUID.randomUUID().toString();
	}
	//	获得相应的时间内容;
	@Override
	public String getCurrentDatetime() {
		long   ltime	   			= System.currentTimeMillis();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		String currenttime 			= dateFormat.format(ltime); 
		return currenttime;
	}
	//	进行IP地址内容的相应信息的配置;
	@Override
	public void getHttpUrl() {
		//	服务器地址
		this.ip		=	this.request.getServerName(); 
		//	端口号
        this.port	=	this.request.getServerPort(); 
        //	项目名称
        this.project= 	this.request.getContextPath();
	}
	
	//	参数的调用;
	public String getIp() {
		return ip;
	}
	public int getPort() {
		return port;
	}
	public String getProject() {
		return project;
	}
	
	
}
