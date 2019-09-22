package com.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tool.DBaseUtil;
import com.tool.Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//import net.sf.json.JSONArray;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/QueryServlet")
public class QueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Util 		util	   = new Util(request);
		
		//	设置网络的返回值的相应内容;
		util.setHttpServletParameter(request, response);
		
		//	进行相应的数据类型;
		String      lcommand   = null; 
				
		//	读取相应的站点信息;
		String 	    stationid  = null;
		
		//	设备的ip地址信息;
		String 		deviceip   = null;
		//	结果字符串;
		String 		result	   = "操作失败";
		//	每页的限制条数;
		int 		limitcount = 0;
		int 		currentpage= 0;
		int 		oper 	   = 0;
		int 		first	   = 0;
		try {
			lcommand	 = request.getParameter("lcommand");	
			deviceip 	 = request.getParameter("deviceip");
			stationid 	 = request.getParameter("stationid");
			
			limitcount	 = Integer.parseInt(request.getParameter("limit").toString());
			currentpage	 = Integer.parseInt(request.getParameter("page").toString());
			first	     = (currentpage-1)*limitcount;
			
			System.out.println("操作正常");
			oper	     = 1;
		} catch (Exception e) {
			System.out.println("异常处理");
			oper	     = 0;
		}
		
//		System.out.println("lcommand="+lcommand+"|stationid="+stationid+"|deviceip="+deviceip+"|limit="+limitcount+"|currentpage="+currentpage);
		
		//	进行相应的数据查询;
		result			 = getResultInfo(util,	oper, lcommand, stationid, deviceip, first, limitcount);
//		System.out.println("结果>>\r\n"+result);
	
		
		//	返回相应的数据内容;
        response.getWriter().write(result);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String getResultInfo(Util util,int oper,String lcommand,String stationid,String deviceip,int first,int limitcount) {
		String 		result	   = null;
		DBaseUtil 	baseUtils  = new DBaseUtil();
		String 		sqlall	   = "";
		String 		sql	  	   = "";
		switch (oper) {
		//	翻页查询;
		case 1:
			ArrayList<String> list=new ArrayList<String>();
			
			if(lcommand!=null&&!lcommand.trim().equals("")) {
				list.add("lcommand='"+lcommand+"'");
			}
			
			if(stationid!=null&&!stationid.trim().equals("")) {
				list.add("stationid='"+stationid+"'");
			}
			
			if(deviceip!=null&&!deviceip.trim().equals("")) {
				list.add("deviceip='"+deviceip+"'");
			}
			String where="";
			if(list.size()!=0) {
				for(String item:list) {
					where+=" "+item+" and";
				}
				where="where"+where.subSequence(0, where.length()-"and".length());	
			}
			
			//	去除最后一个and;
//			//	进行相应的查询内容;
			sql   = "select * from monitorinfo "+where+" order by datetime1 desc limit "+first+","+limitcount;
			sqlall= "select count(lcommand) from monitorinfo "+where+" order by datetime1 desc";
			break;
			
		default:
			//	进行相应的查询内容;
			sql   = "select * from monitorinfo";
			break;
		}
//		System.out.println("sql=\r\n"+sql);
		//	数据库查询操作;
		JSONArray 	 		list    = baseUtils.select2(util,sql);
		int 				count	= baseUtils.getQueryCount(sqlall);
//		ArrayList<String[]> listall = baseUtils.select(sqlall);
		
		result		       = list.toString();
		JSONObject  object = new JSONObject();
		
//		System.out.println("总数="+listall.toString());
		
		object.put("code", "200");
		object.put("msg", "正常");
		object.put("count", count);
		object.put("data", result);
//		//	进行数据库的关闭;
		baseUtils.close();
		return object.toString();
	}
	

}
