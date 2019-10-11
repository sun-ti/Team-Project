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
			// TODO
			result			= query.query();	
			break;
			
		//	车牌识别;
		case 1:
			//	TODO
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
			// TODO
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
			
		//	入站率分析-各月入站率的变化趋势√;
		case 7:
			result			= query. queryRadio_InStation_ByMonth();
			break;
			
		//	按照牌照的次数进行调查-车牌进站率的比较√;
		case 8:
			result			= query.query_licence();
			break;
			
		//	按照用户的类别区分各类用户加油次数的变化趋势√;
		case 9:
			result			= query.query_addOilCountByDifferentKind();
			break;
			
		//	按照站点各个月份点的车辆和入店比率的内容√;
		case 10:
			result			= query.query_RadioByHumanAndCar();
			break;
		
		//	按照站点各个月份点的人流数量趋势比√;
		case 11:
			result			= query.query_InStationByMonth();
			break;
		//	加油停留时间的查询√;
		case 12:
			result			= query.query_stayStationByMonth();
			break;
			
		//	加油停留时间与停留关系的查询√;
		case 13:
			result			= query.query_stayStationByMonth();
			break;
			
		//	选择站点（列表来自站点管理）和时间（按天），显示系统采集接口采集的配送单数据√;
		case 14:
			result			= query.query_DeliveryOrder();
			break;
		
		//	按天记录各站卸油车实际卸油时间和配送单对应情况√
		case 15:
			result			= query.query_DeilveryAndReality();
			break;
		
		//	按天记录各站卸油车实际卸油时间和配送单对应情况√
		case 16:
			result			= query.query_DeilveryAndReality();
			break;

		//	按天记录各站卸油车实际卸油时间和配送单对应情况√
		case 17:
			result			= query.query_DeilveryAndReality();
			break;
			
		//	选择站点（列表来自站点管理），统计分析单站各月卸油车和配送单比对情况
		case 18:
			result			= query.query_AnalyzeDeilveryAndOrder();
			break;
		
		//	统计分析各加油站卸油车和配送单比对情况
		case 19:
			result			= query.query_AnalyzeDeilveryAndOrder();
			break;
			
		//17.选择站点（列表来自站点管理），登记黑名单车辆信息
		case 20:
			result			= query.add_ExceptionList();
			break;
		 
	    //18.记录所有站点黑名单车辆到站信息。可以按照站点排序
		case 21:
			result			= query.query_ExceptionListByStationId();
			break;
		 
		//19.显示各站黑名单车辆到站预警（并需传输给CMC系统）
		case 22:
			result			= query.query_ExceptionListByStationId();
			break;
		 
		//20.用柱状图横向比较各加油站黑名单车辆到站事件数量	
		case 23:
			result			= query.alarm_ExceptionToStation();
			break;
		//21.根据SQL语句进行查询
		case 24:
			result			= query.query_bySQL();
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
