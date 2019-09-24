package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.process.Query_Monitor;
import com.model.Util_Net;

//import net.sf.json.JSONArray;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/QueryServlet_Monitor")
public class QueryServlet_Monitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryServlet_Monitor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Util_Net 	  util_Net  = new Util_Net(request,response);
		
		//	设置网络的返回值的相应内容;
		util_Net.setHttpServletParameter();
		String 		  result	= "操作失败";
		//	获得结果的标签;
		int 		  oper	    = Integer.parseInt(request.getParameter("oper"));
		Query_Monitor query		= new Query_Monitor(util_Net);
		
		switch (oper) {
		
		//	进行所有信息的查询;
		case 0:
			result			= query.query();	
			break;
			
		//	车牌识别;
		case 1:
			result			= query.queryByVehicle_license();
			break;
		
		//	车流识别-统计-按日;
		case 2:
			result			= query.queryByVehicle_flow_Day();
			break;
		//	车流识别-统计-按月;
		case 3:
			result			= query.queryByVehicle_flow_Month();
			break;		
		//	人脸识别;
		case 4:
			result			= query.queryByFace_recognition();
			break;
			
		//	人流识别-统计-按日;
		case 5:
			result			= query.queryByFace_flow_Day();
			break;
		
		//	人流识别-统计-按月;
		case 6:
			result			= query.queryByFace_flow_Month();
			break;
			
		default:
			break;
		}
		
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

}
