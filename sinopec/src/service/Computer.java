package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.Util_Net;

import model.DBaseUtils;
import model.Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//	查询车牌信息;
public class Computer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//	时间区域的标记;
	private int    minute	=	2;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Computer() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//	获取的参数内容;
		String 	   parameters= null;
		//	获取的参数对象;
		JSONObject object    = null;
		String 	   clientIp	 = null;
		String 	   oilGunNum = null;
		//	进行数据库操作;
		DBaseUtils baseUtils = new DBaseUtils();
		JSONArray  array	 = null;
		JSONObject back	 	 = new JSONObject();
		double	   hour		 = 60*60*1000;
		
		//	结果标签;
		String 	   tag		 = "errorDesc";
		String 	   sql		 = "";
		
		//	返回编码;
		String 	   errCode	 = "1";
		//	返回结果;
		String 	   result	 = null;
		
		//	开头设置;
		Util_Net 	util_Net = new Util_Net(request,response);
		
		util_Net.setHttpServletParameter();
		try {
			
			//	方法的解析;
			System.out.println("实现getEntrancePlates");
			//	获得参数信息的内容;
			parameters		 = request.getParameter("parameters").toString().trim();
			
			//	获取相应的数据信息并且进行过滤;
			if(parameters!=null&&!parameters.equals("")) {
				
				//	判断参数中是否有相同的内容变量;
				if(parameters.contains("\"{")) {
					parameters=parameters.replace("\"{", "{");
				}
				
				if(parameters.contains("}\"")) {
					parameters=parameters.replace("}\"", "}");
				}
				
				//	对进行过滤之后的数据进行参数的获取;
				object			 = JSONObject.fromObject(parameters);
				
				//	获取油机的IP地址;
				clientIp		 = object.getString("clientIp");
				
				//	获取油机的编号;
				oilGunNum		 = object.getString("oilGunNum");
								
				//	1.根据上传的加油机的ip地址查询出所在的stationid;
				sql				 = "select a.stationid from zhandian_jiayouji a,shebei_jiayouji b where a.jiayouji_id=b.jiayouji_id and b.jiayouji_ip='"+clientIp+"'";
				array			 = baseUtils.select2(sql);
				
				//	查询出了相应的站点的结果;
				if(array.size()!=0) {
					
					//	获得当前的站点结果;
					String stationid = array.getJSONObject(0).getString("stationid").toString();
					System.out.println("站点编号:"+stationid+" 油机的编号:"+oilGunNum);
					
					//	检测加油的表单是否存在;
					checkTable(baseUtils, stationid);

					//	获得当前的时间;
					long     t2		 =	System.currentTimeMillis();
					long   	 dis	 =	minute*60*1000L;
					
					//	获得当前时间组;
					long[]   today	 =	Util.getCurrentTimeStrs(t2);
					
					long	 today1  =  today[0];
					long	 today2  =  today[1];
					
					
					
					//	当前值向前-10分钟,获得现在的站点值;
					long   t1		=	t2-dis;
					//	从监控表中获取相应的数据信息;
					sql				=	"select a.plate from tongji_"+stationid+"_jiankong a ,shebei_shexiangtou b, shebei_jiayouji c, jiayouji_shexiangtou d where a.deviceip=b.shexiangtou_ip and c.jiayouji_id=d.jiayouji_id and b.shexiangtou_id=d.shexiangtou_id and c.jiayouji_ip='"+clientIp+"' and a.plate!='无' and a.jiankong_id between "+t1+" and "+t2;

					//	进行查询的内容;
					array			 = baseUtils.select2(sql);
					JSONObject 	obj	 = new JSONObject();
					if(array!=null) {
						if(array.size()!=0) {
							
							//	查重数据的数据列表信息;
							Set<String> 	set		=	new TreeSet<String>();
							
							//	进行数据结果的遍历;
							for(int i=0;i<array.size();i++) {

								//	当前的车牌;
								String 		plates 	= 	array.getJSONObject(i).getString("plate").trim();

								//	只在当天的加油信息;
								sql					    	= "select jiayou_id from tongji_"+stationid+"_jiayou where plate='"+plates+"' and jiayou_id between "+today1+" and "+today2+" order by jiayou_id desc limit 0,1";

								//	获得当前的数据结果集合;
								ArrayList<String[]> list	= baseUtils.select(sql);

								//	表明能够查询出相应数据;
								if(list.size()!=0) {

									//	时间的长整型字符串;
									String 			date	= list.get(0)[0];
									
									//	将时间转换为长整型;
									double			ddate	= Double.parseDouble(date);

									double		    dcurrent= Double.parseDouble(t2+"");
									
									//	判断与当前的时间间隔;
									double			dcha	= dcurrent-ddate;

									//	当加油差值>=1小时的情况下;
									if(dcha>=hour) {
										
										//	添加车牌;
										String temp="\""+plates+"\"";
										//	将不重复的数据添加到数组中;
										set.add(temp);
									}									
								}
								//	进行数据的插入;
								else {
									//	添加车牌;
									String temp="\""+plates+"\"";
									//	将不重复的数据添加到数组中;
									set.add(temp);
								}
							}
							//	进行相应的数据返回值;
							obj.put("plates", set.toString());
							//	结果的标志位数;
							errCode				= "0";
							tag					= "result";
							//	将结果进行返回;
							result				= obj.toString();
							
						}else
							result				= "查询结果为空";
					}else
						result				= "查询失败";
					
				}else
					result		= "查询结果为空";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		back.put("errorCode", errCode);
		back.put(tag, result);

		//	结果返回给前端页面中;
		response.getWriter().append(back.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	//	进行表的数据信息的查询;
	private void checkTable(DBaseUtils baseUtils,String stationid) {
		//	进行检测已经加油的车牌信息;
        String tablename =	"tongji_"+stationid+"_jiayou_chepai";
        String dbname	 =	"sinopec";
        
        String sql       = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+dbname+"' and TABLE_NAME='"+tablename+"'";
        
        ArrayList<String[]>list     = baseUtils.select(sql);
        
        //	当表单不存在的话;
        if(list.size()==0) {
        	 //  进行表单的创建;
            sql="create table "+tablename+" (" +
                "autoid bigint primary key auto_increment," +
                "plate bigint," +
                "datetime bigint" +
                ")";
            //  进行表单的操作;
            baseUtils.update(sql);
        }
	}
}
