package com.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.Util_DBase;
import com.model.Util_Net;

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
		String folder="D:\\apache-tomcat-9.0.22-windows-x64\\apache-tomcat-9.0.22\\webapps\\ExtractLoadV01\\SYS\\SINOPEC\\hcpic\\i1\\172.30.9.13";
		
		File file=new File(folder); 
		
		File[] files= file.listFiles();
		
		files[0].getName();
		System.out.println(files.length);
		int max=60766;
		
		
		String sql="select uuid,pic1,pic2 from monitorinfo ";
		
		
		
//		String starttime= "2018-10-09 00:00:00";
////		
//		long   lst		= util_DBase.transDateStr2Long(starttime, "yyyy-MM-dd HH:mm:ss");
//		long   dt		= 24*3600000;
//		
//		long   datetime1		=	lst;
//		
//		for(int i=0;i<365;i++) {
//			
//
//			for(int j=0;j<4;j++) {
//		
//				String uuid		=	util_DBase.getUUID();
//				String orderNum	=	String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10));
//				double price	=	(50+util_DBase.getRandomNum(10)*0.15*100);
//				int    quantity =	(int) (util_DBase.getRandomNum(10)*0.15*100);
//				String money	=	price*quantity+"";
//				String payType	=	(util_DBase.getRandomNum(2)+1)+"";
//				String cardNum  =	"J"+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10));
//				String oilGunNum=	String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10));
//				
//				String startTime=	util_DBase.getCurrentDatetime(datetime1, Util.TAG_DATETIME);
//				
//				long   l1		=	util_DBase.transDateStr2Long(startTime, Util.TAG_DATETIME);
//				
//				
//				long   dl		=	(long) (util_DBase.getRandomNum(3)*0.15*10*3600000);
//				
//				long   l2		=	l1+dl;
//				
//				String endTime  =	util_DBase.getCurrentDatetime(l2, Util.TAG_DATETIME);
//				
//				
//				String plateOld =	"津"+util_DBase.getRandommEng26()+util_DBase.getRandommEng26()+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10));
//				String plate	=	"津"+util_DBase.getRandommEng26()+util_DBase.getRandommEng26()+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10))+String.valueOf(util_DBase.getRandomNum(10));
//				String state	=	"0";
//				
//				
//				String sql="insert into jyj (uuid,orderNum,price,quantity,money,payType,cardNum,oilGunNum,startTime,endTime,plateOld,plate,state) values ("
//						+ "'"+uuid+"','"+orderNum+"',"+price+","+quantity+","+money+","+payType+",'"+cardNum+"','"+oilGunNum+"','"+startTime+"','"+endTime+"','"+plateOld+"','"+plate+"',"+state+")";
////				System.out.println(sql);
//				util_DBase.update(sql);
//
//				
//			}
//		
//			datetime1+=dt;
//		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		String sql="select cp_no ,datetime,datetime1,year,month,day from car_removeoil where state=0";
//		
//		JSONArray array=util_DBase.select(sql);
//		
//		for(int i=0;i<array.size();i++){
//			
//			String uuid		=	util_DBase.getUUID();
//			String carid	=	array.getJSONObject(i).getString("cp_no");
//			String stationid=	"jyxd";
//			String datetime	=	array.getJSONObject(i).getString("datetime");
//			String datetime1=	array.getJSONObject(i).getString("datetime1");
//			String year		=	array.getJSONObject(i).getString("year");
//			String month	=	array.getJSONObject(i).getString("month");
//			String day		=	array.getJSONObject(i).getString("day");
//
//			sql		=	"insert into stationstatistics_exception (uuid,carid,stationid,datetime,datetime1,year,month,day) values ("
//					+ "'"+uuid+"','"+carid+"','"+stationid+"','"+datetime+"',"+datetime1+","+year+","+month+","+day+")";
//			
//			util_DBase.update(sql);
//		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		String starttime= "2018-10-09 00:00:00";
//		
//		long   lst		= util_DBase.transDateStr2Long(starttime, "yyyy-MM-dd HH:mm:ss");
//		long   dt		= 24*3600000;
//		
//		long   datetime1		=	lst;
//		
//		for(int i=0;i<365;i++) {
//			
////			autoid
//			String uuid				=	util_DBase.getUUID();
//			String stationid		=	"jyxd";
//			int    out				= 	(int) (50+(util_DBase.getRandomNum(10)*0.5)*20);
//			String out_num			= 	out+"";
//			String in_num			= 	(out-(util_DBase.getRandomNum(10)*0.5)*20)+"";
//			String in_oil_quantity	=	(util_DBase.getRandomNum(10)*0.5)*20+"";
//			String in_oil_count		=	util_DBase.getRandomNum(2)+"";
//			String datetime			=	util_DBase.getCurrentDatetime(datetime1, "yyyy-MM-dd HH:mm:ss");
//			
//			//	对当前的时间进行获取;
//			String date			=	util_DBase.getCurrentDatetime(datetime1, "yyyy-MM-dd");
//			
//			String[] dates		=	date.split("-");
//			
//			String year			=	dates[0];
//			
//			String month		=	dates[1];
//			
//			String day			=	dates[2];
//			
//			String sql="insert into stationstatistics_carandoil (uuid,stationid,out_num,in_num,in_oil_quantity,in_oil_count,datetime,datetime1,year,month,day) values("
//					+ "'"+uuid+"','"+stationid+"',"+out_num+","+in_num+","+in_oil_quantity+","+in_oil_count+",'"+datetime+"',"+datetime1+","+year+","+month+","+day+")";
//			
//			util_DBase.update(sql);
//			
//			datetime1+=dt;
//		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		for(int i=0;i<40;i++) {
//			
//			for(int j=0;j<4;j++) {
//				
//				String uuid			=	util_DBase.getUUID();
//				String psd_id		=	util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+"";
//				String stationid	=	"jyxd";
//				
//				
//		        String first		=	util_DBase.getRandommEng26();
//		        String second		=	util_DBase.getRandommEng26();
//		        String third		=	util_DBase.getRandommEng26();
//					
//			      
//	        	//	形成车牌号
//		        String cp_no		=	"津"+first+second+third+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10);
//				
//				String psd_time		=	util_DBase.getCurrentDatetime(datetime1, "yyyy-MM-dd HH:mm:ss");
//				
//				System.out.println("原"+psd_time);
//				
//				long   lpsd			=	util_DBase.transDateStr2Long(psd_time, "yyyy-MM-dd HH:mm:ss");
//				
//				long   ldatetime1	=	lpsd+12*3600000;
//				
//				String ldatetime	=	util_DBase.getCurrentDatetime(ldatetime1, "yyyy-MM-dd HH:mm:ss");
//				
//				String car_id		=	cp_no;
//				
//				if(i%3==0) {
//					first			=	util_DBase.getRandommEng26();
//					second			=	util_DBase.getRandommEng26();
//					third			=	util_DBase.getRandommEng26();
//					
//					//	形成车牌号
//					car_id			=	"津"+first+second+third+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10);
//				}
//				
//	
//				//	对当前的时间进行获取;
//				String date			=	util_DBase.getCurrentDatetime(ldatetime1, "yyyy-MM-dd");
//				
//				String[] dates		=	date.split("-");
//				
//				String state		=	"1";
//				
//				if(!cp_no.equals(car_id)) {
//					state		=	"0";
//				}
//				
//				String year			=	dates[0];
//				
//				String month		=	dates[1];
//				
//				String day			=	dates[2];
//				
//				String sql="insert into car_removeoil (uuid,psd_id,stationid,cp_no,car_id,datetime,datetime1,psd_time,state,year,month,day) values ("
//						+ "'"+uuid+"','"+psd_id+"','"+stationid+"','"+cp_no+"','"+car_id+"','"+ldatetime+"',"+ldatetime1+",'"+psd_time+"',"+state+","+year+","+month+","+day+")";
//				
//				System.out.println(sql);
//				util_DBase.update(sql);
//				
//			}
//			datetime1			   +=	dt;
//		}
		
		
		
		
		
		
		
		
		
//		//	进行数据库操作的内容;
//		String sql="select psd_id,cp_no,ps_date from ywy";
//	
//		
//		JSONArray array=util_DBase.select(sql);
//		
//		for(int i=0;i<array.size();i++) {
//			
//			String uuid			=	util_DBase.getUUID();
//			String psd_id		=	array.getJSONObject(i).getString("psd_id");
//			String stationid	=	"jyxd";
//			String cp_no		=	array.getJSONObject(i).getString("cp_no");
//			
//			String psd_time		=	array.getJSONObject(i).getString("ps_date");
//			
//			System.out.println("原"+psd_time);
//			
//			long   lpsd			=	util_DBase.transDateStr2Long(psd_time, "yyyy-MM-dd HH:mm:ss");
//			
//			long   datetime1	=	lpsd+12*3600000;
//			
//			String datetime		=	util_DBase.getCurrentDatetime(datetime1, "yyyy-MM-dd HH:mm:ss");
//			
//			System.out.println("新"+datetime);
//			
//			String car_id		=	cp_no;
//			
//			if(i%3==0) {
//				String first	=	util_DBase.getRandommEng26();
//				String second	=	util_DBase.getRandommEng26();
//				String third	=	util_DBase.getRandommEng26();
//				
//		      
//				//	形成车牌号
//				car_id			=	"津"+first+second+third+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10);
//			}
//			
//
//			//	对当前的时间进行获取;
//			String date			=	util_DBase.getCurrentDatetime(datetime1, "yyyy-MM-dd");
//			
//			String[] dates		=	date.split("-");
//			
//			String state		=	"1";
//			
//			if(!cp_no.equals(car_id)) {
//				state		=	"0";
//			}
//			
//			String year			=	dates[0];
//			
//			String month		=	dates[1];
//			
//			String day			=	dates[2];
//			
//			sql="insert into car_removeoil (uuid,psd_id,stationid,cp_no,car_id,datetime,datetime1,psd_time,state,year,month,day) values ("
//					+ "'"+uuid+"','"+psd_id+"','"+stationid+"','"+cp_no+"','"+car_id+"','"+datetime+"',"+datetime1+",'"+psd_time+"',"+state+","+year+","+month+","+day+")";
//			
//			System.out.println(sql);
//			util_DBase.update(sql);
//		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		int max=365;
//		String starttime= "2018-10-09 00:00:00";
//		long   lst		= util_DBase.transDateStr2Long(starttime, "yyyy-MM-dd hh:mm:ss");
//		long   dt		= 24*3600000;
//		
//		long   datetime1		=	lst;
//		int    count			=	0;
//		//	365天的数据;
//		for(int i=0;i<max;i++) {
//			
//			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//			//	人脸流量统计表;
////			autoid
//			String uuid				=	util_DBase.getUUID();
//			String stationid		=	"jyxd";
//			String in_human_num		=	(util_DBase.getRandomNum(20)*0.5*100)+"";
//			
//			String datetime			=	util_DBase.getCurrentDatetime(datetime1, "yyyy-MM-dd hh:mm:ss");
//
//			//	对当前的时间进行获取;
//			String date				=	util_DBase.getCurrentDatetime(datetime1, "yyyy-MM-dd");
//			
//			String[] dates			=	date.split("-");
//			
//			String year				=	dates[0];
//			
//			String month			=	dates[1];
//			
//			String day				=	dates[2];
////			autoid
////			uuid
////			stationid
////			in_human_num
////			datetime
////			datetime1
////			year
////			month
////			day
//
//			sql						=	"insert into stationstatistics_human "
//					+ "(uuid,stationid,in_human_num,datetime,datetime1,year,month,day) values ("
//					+ "'"+uuid+"','"+stationid+"',"+in_human_num+",'"+datetime+"',"+datetime1+","+year+","+month+","+day+")";
//			
//			
//			util_DBase.update(sql);
//			count++;
//			System.out.println("第"+count+"条数据插入");
//			
//			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//			// 汽车加油表的统计;
////			//	每天500辆车的数据;
////			for(int j=0;j<300;j++) {
////				
//////			String uuid				=	util_DBase.getUUID();
//////	        String first			=	util_DBase.getRandommEng26();
//////	        String second			=	util_DBase.getRandommEng26();
//////	        String third			=	util_DBase.getRandommEng26();
//////				
//////		      
//////        	//	形成车牌号
//////	        String carid			=	"津"+first+second+third+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10)+util_DBase.getRandomNum(10);
//////			String stationid		=	"jyxd";
//////			
//////			String in_oil_quantity	=	(util_DBase.getRandomNum(20)+1)+"";
//////			
//////			String in_oil_count		=	util_DBase.getRandomNum(2)+"";
//////			
//////			String datetime			=	util_DBase.getCurrentDatetime(datetime1, "yyyy-MM-dd hh:mm:ss");
//////			
//////			String staytime			=	(util_DBase.getRandomNum(5)*0.5*10)+"";
//////			
//////			//	对当前的时间进行获取;
//////			String date				=	util_DBase.getCurrentDatetime(datetime1, "yyyy-MM-dd");
//////			
//////			String[] dates			=	date.split("-");
//////			
//////			String year				=	dates[0];
//////			
//////			String month			=	dates[1];
//////			
//////			String day				=	dates[2];
//////			
//////			//	获取类型的内容;
//////			String type				=	(util_DBase.getRandomNum(2)+1)+"";
//////
//////			
//////			sql="insert into car_addoil (uuid,carid,stationid,in_oil_quantity,in_oil_count,datetime,datetime1,staytime,year,month,day,type) values("
//////					+ "'"+uuid+"','"+carid+"','"+stationid+"',"+in_oil_quantity+","+in_oil_count+",'"+datetime+"',"+datetime1+","+staytime+","+year+","+month+","+day+","+type+")";
////
////			util_DBase.update(sql);
////			count++;
////			System.out.println("第"+count+"条数据插入");
////			
////			}
//
//			//	进行毫秒数的增加;
//			datetime1			   +=	dt;
//		}
		
		util_DBase.close();
//		
		response.getWriter().append("operate finish");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
