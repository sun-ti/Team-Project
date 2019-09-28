package com.model;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.models.Utils_Net;

import net.sf.json.JSONObject;

//	进行网络内容的管理;
public class Util_Net implements Utils_Net{
	//	IP地址;
	private String ip;
	//	端口号;
	private int    port;
	//	项目名称;
	private String project;
	//	头部链接;
	private String httphead;
	//	项目路径;
	private String propath;
	
	//	参数的内容;
	private HttpServletRequest  request;
	private HttpServletResponse response;

	//	构造函数内容;
	public Util_Net(HttpServletRequest request,HttpServletResponse response) {
		this.request =request;
		this.response=response;
		getHttpUrl();
	}
	
	//	进行IP地址内容的相应信息的配置;
	public void getHttpUrl() {
		//	服务器地址
		this.ip		 =	this.request.getServerName(); 
		//	端口号
        this.port	 =	this.request.getServerPort(); 
        //	项目名称
        this.project = 	this.request.getContextPath();
        //	链接的头部;
        this.httphead=	"http://"+this.ip+":"+this.port;
        
        //	项目的路径;
        this.propath =	this.request.getSession().getServletContext().getRealPath("\\");
	}

	//	Ip地址;
	public String getIp() {
		return ip;
	}
	public String getPropath() {
		return propath;
	}

	//	Port端口号;
	public int getPort() {
		return port;
	}
	//	Project项目;
	public String getProject() {
		return project;
	}
	//	Http的头部信息;
	public String getHttphead() {
		return httphead;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	//	设置参数和过滤器;
	public void setWebFilter() {
		
		/* 允许跨域的主机地址 */
		this.response.setHeader("Access-Control-Allow-Origin", "*");  
		/* 允许跨域的请求方法GET, POST, HEAD 等 */
		this.response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");  
		/* 重新预检验跨域的缓存时间 (s) */
		this.response.setHeader("Access-Control-Max-Age", "3600");  
		/* 允许跨域的请求头 */
		this.response.setHeader("Access-Control-Allow-Headers", "*");  
		/* 是否携带cookie */
		this.response.setHeader("Access-Control-Allow-Credentials", "true");  
	}
	
	//	设置网页的相应内容;
	public void setHttpServletParameter(){
		//	输入的字符串编辑内容;
		try {
			this.request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	输出的相应的内容信息;
		this.response.setHeader("Context-Type","text/html;charset=utf-8");
		this.response.setCharacterEncoding("UTF-8");
		this.response.setContentType("text/html;charset=utf-8");
		
	}
	//	传输结果打包;
	public String sendResult(String code,String Msg,int count,String data) {
		JSONObject object=new JSONObject();
		object.put("code", code);
		object.put("msg", Msg);
		object.put("count", count);
		object.put("data", data);
		return object.toString(); 
	}
	
}
