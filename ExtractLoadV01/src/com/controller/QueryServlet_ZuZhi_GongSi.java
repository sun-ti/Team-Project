package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.model.Util_Net;
import com.process.Query_ZuZhi_GongSi;

//import net.sf.json.JSONArray;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/QueryServlet_ZuZhi_GongSi")
public class QueryServlet_ZuZhi_GongSi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryServlet_ZuZhi_GongSi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
				//	开头设置;
				Util_Net 			util_Net= new Util_Net(request,response);
				
				//	设置网络的返回值的相应内容;
				util_Net.setHttpServletParameter();
				String 		   		result  = "";
				int 		   		oper	= Integer.parseInt(request.getParameter("oper"));
				
				Query_ZuZhi_GongSi  query   = new Query_ZuZhi_GongSi(util_Net);
				
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
					
				//	根据公司查询区域;
				case 4:
					result=query.queryQuYuByGongSi();
					break;
					
				//	根据公司新增区域;
				case 5:
					result=query.addQuYuByGongSi();
					break;
					
				//	根据公司删除区域;
				case 6:
					result=query.delQuYuByGongSi();
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
