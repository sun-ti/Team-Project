package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.model.Util_Net;
import com.process.Query_ZuZhi_QuYu;

//import net.sf.json.JSONArray;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/QueryServlet_ZuZhi_QuYu")
public class QueryServlet_ZuZhi_QuYu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryServlet_ZuZhi_QuYu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
				//	开头设置;
				Util_Net 	util_Net   = new Util_Net(request,response);
				
				//	设置网络的返回值的相应内容;
				util_Net.setHttpServletParameter();
				String 		     result  = "";
				int 		     oper	 = Integer.parseInt(request.getParameter("oper"));
				
				Query_ZuZhi_QuYu query   = new Query_ZuZhi_QuYu(util_Net);
				
				switch (oper) {
				//	增加
				case 0:
					result=query.add();
					break;
				//	删除
				case 1:
					result=query.del();
					break;
				//	修改
				case 2:
					result=query.updateItem();
					break;
				//	查询
				case 3:
					result=query.query();
					break;
					
				
				//	根据区域查询站点;
				case 4:
					result=query.queryStationByQuYu();
					break;
					
				//	根据区域新增站点;
				case 5:
					result=query.addStationByQuYu();
					break;
					
				//	根据区域删除站点;
				case 6:
					result=query.delStationByQuYu();
					break;	
				default:
					break;
				}
				
				//		返回相应的数据内容;
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
