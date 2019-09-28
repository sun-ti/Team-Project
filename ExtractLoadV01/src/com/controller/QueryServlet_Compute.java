package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.Util_DBase;
import com.model.Util_Net;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class QueryServlet_Computer
 */
@WebServlet("/QueryServlet_Compute")
public class QueryServlet_Compute extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryServlet_Compute() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	网络数据的连接;
		Util_Net   util_Net  = new Util_Net(request, response);
		//	数据库相关操作;
		Util_DBase util_DBase= new Util_DBase();
		//	进行相关的数据库工具;
		util_DBase.LinkDatabase(util_Net);
		
		String sql="select uuid,errMsg from monitorinfo";
		
		JSONArray array=util_DBase.select(sql);
		
		for(int i=0;i<array.size();i++) {
			String uuid  =array.getJSONObject(i).getString("uuid");
			String errMsg=array.getJSONObject(i).getString("errMsg");
			if(errMsg.contains("交通抓拍上传")) {
				String head  = "status:OK,";
				String kind  = errMsg.substring(0,errMsg.indexOf(",交通抓拍上传"));
				kind		 = kind.substring("车辆类型：".length(), kind.length());
				String back	 = errMsg.substring(errMsg.indexOf("车牌：")+"车牌：".length(),errMsg.length());
				String color = "NONE";
				String num   = "NONE";
				
				if(!back.contains("无车牌")) {
					color = back.substring(0,"蓝".length());
					num   = back.substring("蓝".length(),back.length());	
				}
				
//				System.out.println(kind);
				String errNew=head+"type:"+kind+",color:"+color+",num:"+num;
				
				String newsql="update monitorinfo set errMsg='"+errNew+"' where uuid='"+uuid+"'";
				util_DBase.update(newsql);
			}else if(errMsg.contains("人脸抓拍上传")) {
				
//				String head ="人脸抓拍上传，".replace("，", ",");
//				String level=errMsg.substring(errMsg.indexOf("人脸评分")+"人脸评分：".length(),errMsg.indexOf("，年龄段"));
//				String age  =errMsg.substring(errMsg.indexOf("年龄段")+"年龄段：".length(),errMsg.indexOf("，性别"));
//				String sex  =errMsg.substring(errMsg.indexOf("性别")+"性别：".length(),errMsg.length());
//				
//				String errNew=head+"level:"+level+",age:"+age+",sex:"+sex;
//				
//				String newsql="update monitorinfo set errMsg='"+errNew+"' where uuid='"+uuid+"'";
//				util_DBase.update(newsql);
				
			}else if(errMsg.contains("客流量统计")) {
//				String head ="客流量统计，".replace("，", ",");
//				String in   =errMsg.substring(errMsg.indexOf("进入人数")+"进入人数：".length(),errMsg.indexOf("，离开人数"));
//				String out  =errMsg.substring(errMsg.indexOf("离开人数")+"离开人数：".length(),errMsg.indexOf(", byMode"));
//				String other=errMsg.substring(errMsg.indexOf(", byMode"),errMsg.length()).replace(", ", ",");
//				
//				String errNew=head+"in:"+in+",out:"+out+","+other;
				String errNew=errMsg.replace(",, ", ",");
				
				String newsql="update monitorinfo set errMsg='"+errNew+"' where uuid='"+uuid+"'";
				util_DBase.update(newsql);
			}
			
			
			
//			String newsql="update monitorinfo set errMsg='"+errNew+"' where uuid='"+uuid+"'";
//			util_DBase.update(newsql);
			
		}
		util_DBase.close();
		
		
		
		
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
