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
@WebServlet("/QueryServlet")
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
		
		Util_Net 	util_Net   = new Util_Net(request,response);
		//	设置网络的返回值的相应内容;
		util_Net.setHttpServletParameter();
		
		//	进行相应的数据类型;
		String      lcommand   = null, stationid  = null, deviceip = null,result   = "操作失败", limitcount = null,	currentpage= null;
		
		try {
			lcommand	 = request.getParameter("lcommand");	
			deviceip 	 = request.getParameter("deviceip");
			stationid 	 = request.getParameter("stationid");
			
			limitcount	 = request.getParameter("limit");
			currentpage	 = request.getParameter("page");

			System.out.println("操作正常");

		} catch (Exception e) {
			System.out.println("异常处理");

		}
		
		Query_Monitor query_Monitor= new Query_Monitor(util_Net);
		result					   = query_Monitor.query(lcommand, stationid, deviceip, currentpage, limitcount);
		
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
