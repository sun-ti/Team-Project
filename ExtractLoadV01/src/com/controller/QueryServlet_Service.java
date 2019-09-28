package com.controller;

import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

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
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		//后台日志记录
		System.out.println("servlet开始响应请求!  进行webservice的发送请求!");
		
//		Util_Net net=new Util_Net((HttpServletRequest)arg0, (HttpServletResponse)arg1);
//		net.setHttpServletParameter();
//		
		 
//		URL url = null;
//
//		url = new URL("http://10.178.1.160:8080/services/checktank.checktankHttpSoap11Endpoint");
		
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//	    conn.setDoInput(true);
//	    conn.setDoOutput(true);
//	    conn.setRequestMethod("POST");
//	    conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
//
//		
//		Client client;
//		try {
//			client = new Client(url);
//			
//			Object[] result = client.invoke("getHourTankData", new Object[]{"00000000","1"});
//	        System.out.println(result[0]);
//	        
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    
		
//		String endpoint = "http://10.178.1.160:8080/services/checktank.checktankHttpSoap11Endpoint/";
		String endpoint = "http://10.178.1.160:8080/ycs/DataService?wsdl";
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		org.apache.axis.client.Call call;
		try {
			call = (org.apache.axis.client.Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName("GetHourTankData");
			String res=(String)call.invoke(new Object[]{"32600129","02"});
			System.out.print(res);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 
	}

}
