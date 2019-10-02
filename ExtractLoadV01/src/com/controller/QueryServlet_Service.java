package com.controller;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import com.model.Util_DBase;
import com.model.Util_Net;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//import org.codehaus.xfire.client.Client;


/**
 * Servlet implementation class QueryServlet_Service
 */
@WebServlet("/QueryServlet_Service")
public class QueryServlet_Service extends HttpServlet implements Servlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryServlet_Service() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
 
	}
 
	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}
 
	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}
 
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
 
	}
 
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		//	后台日志记录
		Util_Net util_Net=new Util_Net((HttpServletRequest)request, (HttpServletResponse)response);
		//	设置网络的返回值的相应内容;
		util_Net.setHttpServletParameter();
		
		System.out.println("servlet开始响应请求!  进行webservice的发送请求!");
		
		//	数据库的开启;
		Util_DBase dBase =new Util_DBase();
		dBase.LinkDatabase(util_Net);
		
		String date=request.getParameter("date");

		String endpoint 		= "http://10.178.1.160:8080/services/checktank?wsdl";
		String targetnamespace  = "http://webservice.checktank.sh";

		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		org.apache.axis.client.Call call;
		try {

			call = (org.apache.axis.client.Call)service.createCall();
			//	进行相应的末点描述;
			call.setTargetEndpointAddress(endpoint);
			
			// 05.方法;WDSL中的方法描述;
			call.setOperationName(new QName(targetnamespace, "CIF_OR_ID005"));
			
			call.addParameter("nodeno", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
			call.addParameter("deliveryDate", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
			call.setEncodingStyle("UTF-8");
			// 返回值的类型内容;
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

			// 参数:nodeno,outDate
			String res=(String)call.invoke(new Object[]{"32600693",date});
			
			//	对结果集合进行数据的抽取以及插入;
			JSONObject  object=JSONObject.fromObject(res);
			String 		rows=object.getString("rows");
			
			JSONArray   array=JSONArray.fromObject(rows);
			
			for(int j=0;j<array.size();j++) {
				JSONObject o=array.getJSONObject(j);
				String nodeno=o.getString("NODENO");
				
				String xql=o.getString("XQL");
				String ps_date=o.getString("PS_DATE");
				String cp_no=o.getString("CP_NO");
				String depot_code=o.getString("DEPOT_CODE");
				String psd_id=o.getString("PSD_ID");
				String oils_id=o.getString("OILS_ID");
				
				String uuid=dBase.getUUID();
				String sql="insert into ywy (uuid,nodeno,xql,ps_date,cp_no,depot_code,psd_id,oils_id) values("
						+ "'"+uuid+"','"+nodeno+"','"+xql+"','"+ps_date+"','"+cp_no+"','"+depot_code+"','"+psd_id+"','"+oils_id+"')";
				System.out.println(sql);
				dBase.update(sql);
				
			}
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//	关闭数据库;
		dBase.close();
		 
	}

}
