package com.process;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.model.InStation;
import com.model.Util;
import com.model.Util_DBase;
import com.model.Util_Net;
import com.models.Utils_DBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//	查询信息的处理;
public class Query_Monitor extends Util_DBase implements Utils_DBase{

	//	网络的对象;
	private Util_Net 			util_Net;
	
	public Query_Monitor(Util_Net util_Net) {
		super();
		this.util_Net=util_Net;
		//	进行数据库的连接;
		super.LinkDatabase(this.util_Net);
		
	}
	
	//	统计车牌出现的次数的方法;
	private JSONObject query_licenceByOrder(String stationid,String year,String month,long start,long end) {
		JSONObject object=new JSONObject();
		
		String sql="select errMsg from monitorinfo where stationid='"+stationid+"' and datetime1 between "+start+" and "+end;
		JSONArray array=super.select(sql);
		
		//	进行计数的内容信息;
		int count=1;
		
		Map<String, String> 	map	=	new HashMap<String, String>();
		
		for(int i=0;i<array.size();i++) {
			
			JSONObject   o = array.getJSONObject(i);
			
			//	进行MSG的信息内容;
			String  errMsg = o.getString("errMsg").trim();
			
			//	进行标签的内容;
			if(errMsg.contains("num:")) {

				errMsg=errMsg.substring(errMsg.indexOf("num:")+"num:".length(), errMsg.length());
				
				//	车牌的牌照不同;
				if(!errMsg.equals("NONE")) {
					String temp = null;
					try {
						temp	= map.get(errMsg);
					} catch (Exception e) {
						
					}
					//	进行计数信息;
					if(temp!=null) {
						count++;
						
					}else {
						count=1;
					}
					//	对车牌的出现次数进行赋值;
					map.put(errMsg, count+"");	
				}
					
			}	
		}
		
		ArrayList<InStation> list=new ArrayList<InStation>();

		//	进行排序;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String name  =	entry.getKey();
			int    value =	Integer.parseInt(entry.getValue());
			
			list.add(new InStation(name, value));
		}
		//	长度的排列
		object.put("year", year);
		object.put("tag", month);
		int size	=	list.size();
		
		if(size>0) {
			Collections.sort(list);
			
			String temp="";
			for(int i=size-1;i>=0;i--) {
				String key	=list.get(i).key;
				int    value=list.get(i).value;
				
				temp+=key+","+value+";";
			}	
			
			//	进行order放入到列表里;
			object.put("order", temp);
		}else {
			object.put("order", "NONE");
		}

		return object;
	}
	
	
	
	//	查询配送单
	public String query_DeliveryOrder() {
		//	站点的id编号;
		String 	 stationid="",result=null,sql="",date=null;
		String[] results  = {"1","NO"};
		int size=0;
		
		//	站点的内容;
		stationid		=	util_Net.getRequest().getParameter("stationid");
		//	获得的年份;
		date	 		=	util_Net.getRequest().getParameter("date");
		
		String ps_date  = date+" 00:00:00";
		
		sql				= "select autoid,uuid,nodeno,xql,ps_date,cp_no,depot_code,psd_id,oils_id from ywy where nodeno='"+stationid+"' and ps_date='"+ps_date+"'";
		JSONArray array = super.select(sql);
		
		if(array!=null) {
			results[0]= "0";
			results[1]= "OK";
			result	  = array.toString();
			size	  = array.size();
		}

		return util_Net.sendResult(results[0], results[1], size, result);
	}
	
	
	
	//	加油停留时间的查询
	public String query_stayStationByMonth() {
		//	站点的id编号;
		String 	 stationid="",result=null,sql="";
		String[] results  = {"1","NO"};

		JSONArray array=    new JSONArray();
		
		//		其中的表示内容:
		//	year:请求查询的年份内容;
		//	size:结果集合的数据内容;
		//	monthsize:查询出的月的条目结果;
		int 	    year		 = 0,size  	 = 0,monthsize=0,	monthcurrent = 0,	dis	=	0;
		//	获得月份的临时存储结构;
		JSONArray 	monthtemp	 = null;
		//	进行数据的承装内容结构;

		
		//	站点的内容;
		stationid		   = util_Net.getRequest().getParameter("stationid");
		
		//	获得的年份;
		year	 		   = Integer.parseInt(util_Net.getRequest().getParameter("year"));

		//	查询出当前年份的可用月数个数;
		String 		sql_queryMonth  = "select month from car_addoil where year="+year+" and stationid='"+stationid+"' order by datetime1 desc limit 0,1";
		
		monthtemp					= super.select(sql_queryMonth);
		monthsize					= monthtemp.size();

		if(monthsize!=0)
			
			//	进行数据的转换;
			monthcurrent			= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));
		else {
			//	当结果不存在进行年份-1,进行上1年的检测;
			year					= year-1;
			//	并且再次进行数据的查询;
			sql_queryMonth  		= "select month from car_addoil where year="+year+" and stationid='"+stationid+"' order by datetime1 desc limit 0,1";
			monthtemp				= super.select(sql_queryMonth);
			monthsize				= monthtemp.size();
			
			//	当上1年也没有就进行跳出，并且返回连续两年没有信息;
			if(monthsize!=0)
				
				//	进行数据的转换;
				monthcurrent		= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));				
			else {
				result				= year+"-"+(year+1)+"无数据";
				return util_Net.sendResult(results[0], results[1], size, result);
			}
		}

		//	上限与当前月份的差值;
		dis		=	12-monthcurrent;
		
		//	对上一年的数据进行添加;
		if(dis>0) {
			
			//	上一年的索引内容;
			int last	=	year-1;
			for(int i=12-dis;i<=12;i++) {
				
				//	进行SQL;
				sql				=	"select in_oil_quantity,staytime from car_addoil where year="+last+" and month="+i+" and stationid='"+stationid+"'";
				//	结果内容;
				JSONArray array2=	super.select(sql);
				
				double in_oil_quantity_temp=0;
				int    staytime_temp	   =0;
				
				for(int j=0;j<array2.size();j++) {
					JSONObject obj		  = array2.getJSONObject(j);
					String in_oil_quantity= obj.getString("in_oil_quantity");
					String staytime		  = obj.getString("staytime");
					
					double in_quantity	  = Double.parseDouble(in_oil_quantity);
					int    in_staytime	  = Integer.parseInt(staytime);
					
					in_oil_quantity_temp += in_quantity;
					staytime_temp	     += in_staytime;
				}
				
				double in_quantity_mean   = 0;
				double    staytime_mean   = 0;
				
				if(array2.size()>0) {
					in_quantity_mean	=	(double)in_oil_quantity_temp/array2.size();
					staytime_mean	 	=	(double)staytime_temp/array2.size();	
				}
				
				JSONObject 		object	  = new JSONObject();
				object.put("year", last);
				object.put("month", i);
				object.put("in_quantity_mean", in_quantity_mean);
				object.put("add_oil_count", array2.size());
				object.put("staytime_mean", staytime_mean);
				array.add(object);
			}
		}
		
		for(int i=1;i<=monthcurrent;i++) {
			//	进行SQL;
			sql				=	"select in_oil_quantity,staytime from car_addoil where year="+year+" and month="+i+" and stationid='"+stationid+"'";
			//	结果内容;
			JSONArray array2=	super.select(sql);
			
			double in_oil_quantity_temp=0;
			int    staytime_temp	   =0;
			
			for(int j=0;j<array2.size();j++) {
				JSONObject obj		  = array2.getJSONObject(j);
				String in_oil_quantity= obj.getString("in_oil_quantity");
				String staytime		  = obj.getString("staytime");
				
				double in_quantity	  = Double.parseDouble(in_oil_quantity);
				int    in_staytime	  = Integer.parseInt(staytime);
				
				in_oil_quantity_temp += in_quantity;
				staytime_temp	     += in_staytime;
			}
			
			double in_quantity_mean   = 0;
			double    staytime_mean   = 0;
			
			if(array2.size()>0) {
				in_quantity_mean	=	(double)in_oil_quantity_temp/array2.size();
				staytime_mean	 	=	(double)staytime_temp/array2.size();	
			}
			
			JSONObject 		object	  = new JSONObject();
			object.put("year", year);
			object.put("month", i);
			object.put("in_quantity_mean", in_quantity_mean);
			object.put("add_oil_count", array2.size());
			object.put("staytime_mean", staytime_mean);
			array.add(object);
		}

		if(array!=null) {
			results[0]= "0";
			results[1]= "OK";
			result	  = array.toString();
			size	  = array.size();
		}

		return util_Net.sendResult(results[0], results[1], size, result);
	}

	//	加油停留时间与停留次数的查询
	public String query_DeilveryAndReality() {
		//	站点的id编号;
		String 	 stationid="",result=null,date=null,sql="";
		String[] results  = {"1","NO"};
		int size=0;

		//	站点的内容;
		stationid		=	util_Net.getRequest().getParameter("stationid");
		//	获得的年份;
		date	 		=	util_Net.getRequest().getParameter("date")+" 00:00:00";
			
		sql				=	"select autoid,uuid,psd_id,stationid,cp_no,car_id,datetime,datetime1,psd_time,state from car_removeoil where stationid='"+stationid+"' and psd_time='"+date+"'";
		
		JSONArray array =   super.select(sql);
		
		if(array!=null) {
			results[0]= "0";
			results[1]= "OK";
			result	  = array.toString();
			size	  = array.size();
		}
		
		return util_Net.sendResult(results[0], results[1], size, result);
	}
	//	黑名单方法;
	public String add_ExceptionList() {
		String 	uuid	=	getUUID(),carid=null,stationid=null;
		
		long 	ltime	=	System.currentTimeMillis();
		String 	datetime=	getCurrentDatetime(ltime, Util.TAG_DATETIME),datetime1=ltime+"";
		
		String 	date	=	getCurrentDatetime(ltime, Util.TAG_YEAR_MONTH_DAY);
		
		String[] times  =   date.split("-");
		String  year	=	times[0];
		String  month	=	times[1];
		String  day		=	times[2];

		carid			=	util_Net.getRequest().getParameter("carid");
		stationid		=	util_Net.getRequest().getParameter("stationid");
		
		String sql="insert into stationstatistics_exception (uuid,carid,stationid,datetime,datetime1,year,month,day) values "
				+ "('"+uuid+"','"+carid+"','"+stationid+"','"+datetime+"',"+datetime1+","+year+","+month+","+day+")";
		
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}
	//	根据站点查询黑名单;
	public String query_ExceptionListByStationId() {
		//	站点的id编号;
		String 	 result=null;
		String[] results  = {"1","NO"};
		int size=0,year	= 0,monthsize=0,	monthcurrent = 0,	dis	=	0;
		
		//	查询出所有的站点;
		String 	  sql1		= "select stationid from station";
		JSONArray stationids= super.select(sql1);
		JSONArray array		= new JSONArray();
		
		//	获得的年份;
		year	 			= Integer.parseInt(util_Net.getRequest().getParameter("year"));

		//	根据年进行查询;
		for(int j=0;j<stationids.size();j++) {
			
			JSONObject  obj_station  	= stationids.getJSONObject(j);
			String 	    stationid 		= obj_station.getString("stationid");
			
			//	查询出当前年份的可用月数个数;
			String 		sql_queryMonth  =  "select month from stationstatistics_exception where stationid='"+stationid+"' and year="+year+" order by datetime1 desc limit 0,1";
			
			JSONArray   monthtemp		= 	super.select(sql_queryMonth);
			
			monthsize					= 	monthtemp.size();
			String 		info			=	null;
			
			JSONObject 	obj3			=	new JSONObject();
			obj3.put("stationid", stationid);
			
			if(monthsize!=0)
				
				//	进行数据的转换;
				monthcurrent			= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));
			else {
				//	当结果不存在进行年份-1,进行上1年的检测;
				year					= year-1;
				//	并且再次进行数据的查询;
				sql_queryMonth  		= 	"select month from stationstatistics_exception where stationid='"+stationid+"' and year="+year+" order by datetime1 desc limit 0,1";
				monthtemp				= 	super.select(sql_queryMonth);
				monthsize				= 	monthtemp.size();
				
				//	当上1年也没有就进行跳出，并且返回连续两年没有信息;
				if(monthsize!=0)
					
					//	进行数据的转换;
					monthcurrent		= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));				
				else {
					
					monthcurrent		= -1;
				}
			}
			
			if(monthcurrent!=-1) {

				//	上限与当前月份的差值;
				dis		=	12-monthcurrent;
//				System.out.println(dis);
				
				JSONArray array3 = new JSONArray();
				//	对上一年的数据进行添加;
				if(dis>0) {
					
					//	上一年的索引内容;
					int last	=	year-1;
					for(int i=12-dis;i<=12;i++) {
						String 	   sql	  = "select carid,datetime from stationstatistics_exception where stationid='"+stationid+"' and year="+last+" and month="+i;
						
						JSONArray  array2 = super.select(sql);
						
						JSONObject obj2   = new JSONObject();
					
						obj2.put("year", last);
						obj2.put("month", i);
						obj2.put("data", array2.toString());
						array3.add(obj2);
					}
				}

				//	进行相应的年月的查找;
				for(int i=1;i<=monthcurrent;i++){
					
					String 	   sql	  = "select carid,datetime from stationstatistics_exception where stationid='"+stationid+"' and year="+year+" and month="+i;
		
					JSONArray  array2 = super.select(sql);
					
					JSONObject obj2   = new JSONObject();
				
					obj2.put("year", year);
					obj2.put("month", i);
					obj2.put("data", array2.toString());
					array3.add(obj2);
				}
				info			=	array3.toString();
			
			}else {
				result				= year+"-"+(year+1)+"无数据";
				//	结果集合;
				
				info				= result;
			}

			obj3.put("info", info);
			array.add(obj3);
		}
		
		if(array!=null) {
			results[0]= "0";
			results[1]= "OK";
			result	  = array.toString();
			size	  = array.size();
		}
		
		return util_Net.sendResult(results[0], results[1], size, result);
	}
	
	public String query_bySQL() {
		//	根据SQL进行查询;
		String 	  sql=null,result=null;
		String[]  results  = {"1","NO"};
		int 	  size	   = 0;
		JSONArray array=new JSONArray();
		
		//	传输的SQL语句;
		sql	 		=	util_Net.getRequest().getParameter("sql");
		array		=   super.select(sql);
		
		
		if(array!=null) {
			results[0]= "0";
			results[1]= "OK";
			result	  = array.toString();
			size	  = array.size();
		}
		
		return util_Net.sendResult(results[0], results[1], size, result);
	}
	

	//	黑名单车辆到站预警;
	public String alarm_ExceptionToStation() {
			//	站点的id编号;
			String 	 result=null;
			String[] results  = {"1","NO"};
			int size=0,year	= 0,monthsize=0,	monthcurrent = 0,	dis	=	0;
			
			//	查询出所有的站点;
			String 	  sql1		= "select stationid from station";
			JSONArray stationids= super.select(sql1);
			JSONArray array		= new JSONArray();
			
			//	获得的年份;
			year	 			= Integer.parseInt(util_Net.getRequest().getParameter("year"));

			//	根据年进行查询;
			for(int j=0;j<stationids.size();j++) {
				
				JSONObject  obj_station  	= stationids.getJSONObject(j);
				String 	    stationid 		= obj_station.getString("stationid");
				
				//	查询出当前年份的可用月数个数;
				String 		sql_queryMonth  =  "select month from stationstatistics_exception where stationid='"+stationid+"' and year="+year+" order by datetime1 desc limit 0,1";
				
				JSONArray   monthtemp		= 	super.select(sql_queryMonth);
				
				monthsize					= 	monthtemp.size();
				String 		info			=	null;
				
				JSONObject 	obj3			=	new JSONObject();
				obj3.put("stationid", stationid);
				
				if(monthsize!=0)
					
					//	进行数据的转换;
					monthcurrent			= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));
				else {
					//	当结果不存在进行年份-1,进行上1年的检测;
					int last				= year-1;
					//	并且再次进行数据的查询;
					sql_queryMonth  		= "select month from stationstatistics_exception where stationid='"+stationid+"' and year="+last+" order by datetime1 desc limit 0,1";
					monthtemp				= super.select(sql_queryMonth);
					monthsize				= monthtemp.size();
					
					//	当上1年也没有就进行跳出，并且返回连续两年没有信息;
					if(monthsize!=0)
						
						//	进行数据的转换;
						monthcurrent		= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));				
					else {
						
						monthcurrent		= -1;
					}
				}
				
				if(monthcurrent!=-1) {

					//	上限与当前月份的差值;
					dis		=	12-monthcurrent;
//					System.out.println(dis);
					
					JSONArray array3 = new JSONArray();
					//	对上一年的数据进行添加;
					if(dis>0) {
						
						//	上一年的索引内容;
						int last	=	year-1;
						for(int i=12-dis;i<=12;i++) {
							String 	   sql	  = "select carid,datetime from stationstatistics_exception where stationid='"+stationid+"' and year="+last+" and month="+i;
							
							JSONArray  array2 = super.select(sql);
							
							JSONObject obj2   = new JSONObject();
						
							obj2.put("year", last);
							obj2.put("month", i);
							obj2.put("count", array2.size());
							array3.add(obj2);
						}
					}

					//	进行相应的年月的查找;
					for(int i=1;i<=monthcurrent;i++){
						
						String 	   sql	  = "select carid,datetime from stationstatistics_exception where stationid='"+stationid+"' and year="+year+" and month="+i;
			
						JSONArray  array2 = super.select(sql);
						
						JSONObject obj2   = new JSONObject();
					
						obj2.put("year", year);
						obj2.put("month", i);
						obj2.put("count", array2.size());
						array3.add(obj2);
					}
					info			=	array3.toString();
				
				}else {
					result				= (year-1)+"-"+year+"无数据";
					//	结果集合;
					
					info				= result;
				}

				obj3.put("info", info);
				array.add(obj3);
			}
			
			if(array!=null) {
				results[0]= "0";
				results[1]= "OK";
				result	  = array.toString();
				size	  = array.size();
			}
			
			return util_Net.sendResult(results[0], results[1], size, result);
	}
	
	
	public String query_AnalyzeDeilveryAndOrder() {
		//	站点的id编号;
		String 	  stationid="",result=null,sql="";
		String[]  results  = {"1","NO"};
		int 	  size	   = 0,year		 = 0,monthsize=0,	monthcurrent = 0,	dis	=	0;
		JSONArray array	   = new JSONArray();
		
		//	站点的内容;
		stationid		=	util_Net.getRequest().getParameter("stationid");
		//	获得的年份;
		year	 		=	Integer.parseInt(util_Net.getRequest().getParameter("year"));
		
		//	其中的表示内容:
		//	year:请求查询的年份内容;
		//	size:结果集合的数据内容;
		//	monthsize:查询出的月的条目结果;
		
		//	获得月份的临时存储结构;
		JSONArray 	monthtemp	 = null;
		
		//	站点的内容;
		stationid		   = util_Net.getRequest().getParameter("stationid");
		
		//	获得的年份;
		year	 		   = Integer.parseInt(util_Net.getRequest().getParameter("year"));

		//	查询出当前年份的可用月数个数;
		String 		sql_queryMonth  = "select month from car_removeoil where stationid='"+stationid+"' and year="+year+" group by month ORDER BY datetime1 DESC limit 0,1";
		
		monthtemp					= super.select(sql_queryMonth);
		monthsize					= monthtemp.size();

		if(monthsize!=0)
			
			//	进行数据的转换;
			monthcurrent			= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));
		else {
			//	当结果不存在进行年份-1,进行上1年的检测;
			year					= year-1;
			//	并且再次进行数据的查询;
			sql_queryMonth  		= "select month from car_removeoil where stationid='"+stationid+"' and year="+year+" group by month ORDER BY datetime1 DESC limit 0,1";
			monthtemp				= super.select(sql_queryMonth);
			monthsize				= monthtemp.size();
			
			//	当上1年也没有就进行跳出，并且返回连续两年没有信息;
			if(monthsize!=0)
				
				//	进行数据的转换;
				monthcurrent		= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));				
			else {
				result				= year+"-"+(year+1)+"无数据";
				return util_Net.sendResult(results[0], results[1], size, result);
			}
		}

		//	上限与当前月份的差值;
		dis		=	12-monthcurrent;
		
		//	对上一年的数据进行添加;
		if(dis>0) {
			
			//	上一年的索引内容;
			int last	=	year-1;
			for(int i=12-dis;i<=12;i++) {
				sql				  =   "select autoid,uuid,psd_id,stationid,cp_no,car_id,datetime,datetime1,psd_time,state from car_removeoil where stationid='"+stationid+"' and year="+year+" and month="+i;
				JSONArray  array2 =	super.select(sql);
				
				JSONObject object = new JSONObject();
				
				object.put("year", last);
				object.put("month", i);
				object.put("data", array2.toString());
				
				array.add(object);
			}
		}
		
		
		for(int i=1;i<=monthcurrent;i++) {
			
			sql				  =   "select autoid,uuid,psd_id,stationid,cp_no,car_id,datetime,datetime1,psd_time,state from car_removeoil where stationid='"+stationid+"' and year="+year+" and month="+i;
			JSONArray  array2 =	super.select(sql);
			
			JSONObject object = new JSONObject();
			
			object.put("year", year);
			object.put("month", i);
			object.put("data", array2.toString());
			
			array.add(object);
		}
		
		if(array!=null) {
			results[0]= "0";
			results[1]= "OK";
			result	  = array.toString();
			size	  = array.size();
		}
		
		return util_Net.sendResult(results[0], results[1], size, result);
	}
	
	
	//	TODO 1.根据车辆牌照进行查询
	public String queryByVehicle_license() {
		//	站点信息;
		String stationid = "";
		//	每页显示的最大数目,当前的页面;
		String limitcount= null,currentpage=null;
		//	初始页,结束页;
		String start	 = null,end=null;
		long   lstart	 = 0,lend=0;
		
		int    first	 = 1,nlimitcount=10;
		String sql		 = "",sqlAll="";
		
		try {
			
			stationid	 = util_Net.getRequest().getParameter("stationid");
			start		 = util_Net.getRequest().getParameter("start");
			end		 	 = util_Net.getRequest().getParameter("end");
			
			limitcount	 = util_Net.getRequest().getParameter("limit");
			currentpage	 = util_Net.getRequest().getParameter("page");	
		} catch (Exception e) {

		}

		String between	 = "";
		if(start!=null&&!start.equals("")&&end!=null&&!end.equals("")) {

			lstart		 = transDateStr2Long(start, Util.TAG_DATETIME);
			lend		 = transDateStr2Long(end, Util.TAG_DATETIME);
			
			between		 = "and b.datetime1 between "+lstart+" and "+lend;
		}

		//	currentpage判断是否存在;
		if(limitcount!=null&&!limitcount.trim().equals("")) {
			nlimitcount=Integer.parseInt(limitcount);
		}

		if(currentpage!=null&&!currentpage.trim().equals("")) {
			first	     = (Integer.parseInt(currentpage)-1)*nlimitcount;
		}
		String where="";
		if(stationid!=null&&!stationid.trim().equals("")) {
			where+="and a.stationid='"+stationid+"'";
		}
		//	TODO 信息查询;
		sql	  			= "select a.note,b.stationid,b.deviceip,b.errMsg,b.datetime,b.datetime1,b.pic1,b.pic2 from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip "+where+" and a.kind=1 "+between+" order by b.datetime1 desc limit "+first+","+nlimitcount;
		JSONArray array = select(sql);

		//	查询总页数;
		sqlAll			= "select count(b._id) from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip "+where+" and a.kind=1 "+between;
		
		int 	  count = getQueryCount(sqlAll);
		
		return util_Net.sendResult("0", "OK", count, array.toString());
	}
	
	//	2.根据车辆流量进行查询-按日:eg:2019-09-12;
	public String queryByVehicle_flow_Day() {
		setTag("车流识别-按日统计");
		tagStartModule();
		//	id编号;
		String stationid = null;
		
		//	时间戳;
		String date		 = null;
		//	开始的时间戳;
		String start	 = null,end=null;
		//	时间的长整型串;
		long   lstart	 = 0,lend=0;
		
		
		try {
			//	车站的ID编号;
			stationid	 = util_Net.getRequest().getParameter("stationid");
			
			//	获得时间段的内容信息;
			date		 = util_Net.getRequest().getParameter("date");

		} catch (Exception e) {

		}
		
		//	判断输入的时间是否完整;
		if(date!=null&&!date.equals("")) {
			//	当时间信息符合格式时;
			if(checkDate(date, "yyyy-MM-dd")) {
				//	初始的时间;
				start= date+" 00:00:00";
				lstart=transDateStr2Long(start, Util.TAG_DATETIME);
				
				//	结束的时间;
				end	 = date+" 23:59:59";
				lend=transDateStr2Long(end, Util.TAG_DATETIME);
				
			}
			//	当时间信息不符合格式时;
			else
			return util_Net.sendResult("1", "NO", 0, null);
		}
		//	当时间信息输入为错误时;
		else
		return util_Net.sendResult("1", "NO", 0, null);
		//	将时间进行24等分;
		long ldis=(lend-lstart)/24;

		//	进行结果的加载;
		JSONArray array=new JSONArray();
		long l1=lstart,l2=0;
		//	将图像进行24分;
		for(int i=0;i<24;i++) {

			l2=l1+ldis;
			
			//	此时间段的数据;
			//	进行外大街摄像头的检测数据;
			String 		sql1="select b.errMsg from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip and a.stationid='"+stationid+"' and a.kind=5 and b.datetime1 between "+l1+" and "+l2+" order by b.datetime1 desc";
		
			JSONArray array1=super.select(sql1);
			Set<String> set1=new TreeSet<String>();
			//	对大街摄像头数据进行解析;
			for(int a=0;a<array1.size();a++) {
				String errMsg= array1.getJSONObject(a).getString("errMsg").replace(" ", "").trim();
				
				errMsg		 = errMsg.substring(errMsg.indexOf("num:")+"num:".length(), errMsg.length());
				if(!errMsg.equals("NONE")) {
					set1.add(errMsg);
				}
			}
			
			//	进入车站的车牌;
			String 	  sql2	= "select b.errMsg from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip and a.stationid='"+stationid+"' and a.kind=1 and b.datetime1 between "+l1+" and "+l2+" order by b.datetime1 desc";
			JSONArray array2= super.select(sql2);
			Set<String> set2= new TreeSet<String>();
			for(int b=0;b<array2.size();b++) {
				String errMsg=array2.getJSONObject(b).getString("errMsg").replace(" ", "").trim();
				errMsg=errMsg.substring(errMsg.indexOf("num:")+"num:".length(), errMsg.length());
				if(!errMsg.equals("NONE")) {
					set2.add(errMsg);
				}
			}

			int count=0;
			//	用站内车与大街车进行计算;
			for(String item:set2) {
				//	判断大街上是否有车站里的车辆;
				boolean flag=set1.contains(item);
				if(flag) {
					count++;
				}
			}

			l1=l1+ldis;
			JSONObject object=new JSONObject();
			object.put("hour", (i+1));
			object.put("out", set1.size());
			object.put("in", count);
			String res="0";
			if(set1.size()>0) {
				res=((float)count/set1.size())+"";
			}
			
			object.put("percent", res);
			
			array.add(object);
		}

		tagEndModule();
		tagComputeProcessingTime();
		return util_Net.sendResult("0", "OK", 24, array.toString());
	}
	
	//	3.根据车辆流量进行查询-按月;
	public String queryByVehicle_flow_Month() {
		setTag("车流识别-按月统计");
		tagStartModule();
		
		tagStartModule();
		//	id编号;
		String stationid = null;
		
		//	时间戳;
		String date		 = null;
		//	时间内容;
		int    nDate	 = 0;
		
		try {
			//	车站的ID编号;
			stationid	 = util_Net.getRequest().getParameter("stationid");
			
			//	获得时间段的内容信息;
			date		 = util_Net.getRequest().getParameter("date");

		} catch (Exception e) {

		}

		//	判断输入的时间是否完整;
		if(date!=null&&!date.equals("")&&date.length()==4) {
			int nTemp=0;
			try {
				nTemp=Integer.parseInt(date);
			} catch (Exception e) {

			}
			if(nTemp>999&&nTemp<2999) {
				//	参数重新进行赋值;
				nDate=nTemp;
			}//	当时间信息输入为错误时;
			else
				return util_Net.sendResult("1", "NO", 0, null);
			
		}
		//	当时间信息输入为错误时;
		else
		return util_Net.sendResult("1", "NO", 0, null);
		
		//	对12个月进行数据统计;
		JSONArray array=new JSONArray();
		
		for(int i=1;i<=12;i++) {
			
			int j=i+1;
			String start=nDate+"-"+i+"-1 00:00:00";
			String end  =nDate+"-"+j+"-1 00:00:00";
			
			JSONObject object=new JSONObject();
			
			object.put("date", nDate+"-"+i);
			
			
			if(j==13) {
				nDate=nDate+1;
				end=nDate+"-1-1 00:00:00";
			}
			
			long lstart=transDateStr2Long(start, Util.TAG_DATETIME);
			long lend  =transDateStr2Long(end, Util.TAG_DATETIME);
			//	进行相应的数据统计;
			//	此时间段的数据;
			//	进行外大街摄像头的检测数据;
			String 		sql1	=	"select b.errMsg from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip and a.stationid='"+stationid+"' and a.kind=5 and b.datetime1 between "+lstart+" and "+lend+" order by b.datetime1 desc";
			
			JSONArray 	array1	=	super.select(sql1);
			Set<String> set1	=	new TreeSet<String>();
			//	对大街摄像头数据进行解析;
			for(int a=0;a<array1.size();a++) {
				String errMsg=array1.getJSONObject(a).getString("errMsg").replace(" ", "").trim();
				errMsg=errMsg.substring(errMsg.indexOf("num:")+"num:".length(), errMsg.length());
				if(!errMsg.equals("NONE")) {
					set1.add(errMsg);
				}
			}
			
			//	进入车站的车牌;
			String 		sql2	=	"select b.errMsg from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip and a.stationid='"+stationid+"' and a.kind=1 and b.datetime1 between "+lstart+" and "+lend+" order by b.datetime1 desc";
			JSONArray 	array2	=	super.select(sql2);
			Set<String> set2	=	new TreeSet<String>();
			for(int b=0;b<array2.size();b++) {
				String errMsg=array2.getJSONObject(b).getString("errMsg").replace(" ", "").trim();
				errMsg=errMsg.substring(errMsg.indexOf("num:")+"num:".length(), errMsg.length());
				if(!errMsg.equals("NONE")) {
					set2.add(errMsg);
				}
			}
	
			int count=0;
			//	用站内车与大街车进行计算;
			for(String item:set2) {
				//	判断大街上是否有车站里的车辆;
				boolean flag=set1.contains(item);
				if(flag) {
					count++;
				}
			}
			
			object.put("out", set1.size());
			object.put("in", count);
			String res="0";
			if(set1.size()>0) {
				res=((float)count/set1.size())+"";
			}
			
			object.put("percent", res);
			
			array.add(object);
		}

		tagEndModule();
		tagComputeProcessingTime();
		return util_Net.sendResult("0", "OK", 12,array.toString());
	}
	
	//	4.根据人脸识别进行查询;
	public String queryByFace_recognition() {
		//	站点信息;
		String stationid = null;
		//	每页显示的最大数目,当前的页面;
		String limitcount= null,currentpage=null;
		//	初始页,结束页;
		String start	 = null,end=null;
		long   lstart	 = 0,lend=0;
		
		int    first	 = 1,nlimitcount=10;
		String sql		 = "",sqlAll="";
		
		try {
			
			stationid	 = util_Net.getRequest().getParameter("stationid");
			start		 = util_Net.getRequest().getParameter("start");
			end		 	 = util_Net.getRequest().getParameter("end");
			
			limitcount	 = util_Net.getRequest().getParameter("limit");
			currentpage	 = util_Net.getRequest().getParameter("page");	
		} catch (Exception e) {

		}
		
		String between	 = "";
		if(start!=null&&!start.equals("")&&end!=null&&!end.equals("")) {

			lstart		 = transDateStr2Long(start, Util.TAG_DATETIME);
			lend		 = transDateStr2Long(end, Util.TAG_DATETIME);
			
			between		 = "and b.datetime1 between "+lstart+" and "+lend;
		}

		//	currentpage判断是否存在;
		if(limitcount!=null&&!limitcount.trim().equals("")) {
			nlimitcount=Integer.parseInt(limitcount);
		}

		if(currentpage!=null&&!currentpage.trim().equals("")) {
			first	     = (Integer.parseInt(currentpage)-1)*nlimitcount;
		}
		
		//	TODO 信息查询;
		sql	  			= "select a.note,b.stationid,b.deviceip,b.errMsg,b.datetime,b.datetime1,b.pic1,b.pic2 from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip and a.stationid='"+stationid+"' and a.kind=3 "+between+" order by b.datetime1 desc limit "+first+","+nlimitcount;
		JSONArray array = select(sql);
		
		//	查询总页数;
		sqlAll			= "select count(b._id) from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip and a.stationid='"+stationid+"' and a.kind=3 "+between;
		int 	  count = getQueryCount(sqlAll);
		
		return util_Net.sendResult("0", "OK", count, array.toString());
	}
	
	//	5.根据客户流量进行查询-按日;
	public String queryByFace_flow_Day() {
		setTag("车流识别-按日统计");
		tagStartModule();
		//	id编号;
		String stationid = null;
		
		//	时间戳;
		String date		 = null;
		//	开始的时间戳;
		String start	 = null,end=null;
		//	时间的长整型串;
		long   lstart	 = 0,lend=0;

		try {
			//	车站的ID编号;
			stationid	 = util_Net.getRequest().getParameter("stationid");
			
			//	获得时间段的内容信息;
			date		 = util_Net.getRequest().getParameter("date");

		} catch (Exception e) {
			
		}

		//	判断输入的时间是否完整;
		if(date!=null&&!date.equals("")) {
			//	当时间信息符合格式时;
			if(checkDate(date, "yyyy-MM-dd")) {
				//	初始的时间;
				start= date+" 00:00:00";
				lstart=transDateStr2Long(start, Util.TAG_DATETIME);
				
				//	结束的时间;
				end	 = date+" 23:59:59";
				lend=transDateStr2Long(end, Util.TAG_DATETIME);
			}
			//	当时间信息不符合格式时;
			else
			return util_Net.sendResult("1", "NO", 0, null);
		}
		//	当时间信息输入为错误时;
		else
		return util_Net.sendResult("1", "NO", 0, null);
		//	将时间进行24等分;
		long ldis=(lend-lstart)/24;

		//	进行结果的加载;
		JSONArray array=new JSONArray();
		long l1=lstart,l2=0;
		//	将图像进行24分;
		for(int i=0;i<24;i++) {

			l2=l1+ldis;
			
			//	此时间段的数据;
			//	进行外大街摄像头的检测数据;
			String  sql1 = "select count(b._id) from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip and a.stationid='"+stationid+"' and a.kind=3 and b.datetime1 between "+l1+" and "+l2+" order by b.datetime1 desc";

			int 	count= super.getQueryCount(sql1);
			
			l1=l1+ldis;
			JSONObject object=new JSONObject();
			object.put("hour", (i+1));

			object.put("in", count);
			
			array.add(object);
		}

		tagEndModule();
		tagComputeProcessingTime();
		return util_Net.sendResult("0", "OK", 24, array.toString());
	}
	//	6.根据客户流量进行查询-按月;
	public String queryByFace_flow_Month() {
		setTag("人流识别-按月统计");
		tagStartModule();
		
		tagStartModule();
		//	id编号;
		String stationid = null;
		
		//	时间戳;
		String date		 = null;
		//	时间内容;
		int    nDate	 = 0;
		
		try {
			//	车站的ID编号;
			stationid	 = util_Net.getRequest().getParameter("stationid");
			
			//	获得时间段的内容信息;
			date		 = util_Net.getRequest().getParameter("date");

		} catch (Exception e) {

		}

		//	判断输入的时间是否完整;
		if(date!=null&&!date.equals("")&&date.length()==4) {
			int nTemp=0;
			try {
				nTemp=Integer.parseInt(date);
			} catch (Exception e) {

			}
			if(nTemp>999&&nTemp<2999) {
				//	参数重新进行赋值;
				nDate=nTemp;
			}//	当时间信息输入为错误时;
			else
				return util_Net.sendResult("1", "NO", 0, null);
			
		}
		//	当时间信息输入为错误时;
		else
		return util_Net.sendResult("1", "NO", 0, null);
		
		//	对12个月进行数据统计;
		JSONArray array=new JSONArray();
		
		for(int i=1;i<=12;i++) {
			
			int j=i+1;
			String start=nDate+"-"+i+"-1 00:00:00";
			String end  =nDate+"-"+j+"-1 00:00:00";
			
			JSONObject object=new JSONObject();
			
			object.put("date", nDate+"-"+i);
	
			if(j==13) {
				nDate=nDate+1;
				end=nDate+"-1-1 00:00:00";
			}
			
			long lstart=transDateStr2Long(start,Util.TAG_DATETIME);
			long lend  =transDateStr2Long(end, Util.TAG_DATETIME);
			//	进行相应的数据统计;
			//	此时间段的数据;
			//	进行外大街摄像头的检测数据;
			String 		sql1	=	"select count(b._id) from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip and a.stationid='"+stationid+"' and a.kind=3 and b.datetime1 between "+lstart+" and "+lend+" order by b.datetime1 desc";
			
			int 		count	=	super.getQueryCount(sql1);
		
			object.put("in", count);
			
			array.add(object);
		}

		tagEndModule();
		tagComputeProcessingTime();
		return util_Net.sendResult("0", "OK", 12,array.toString());
	}
	
	
	
	//	7.入站率分析-各月入站率的变化趋势;
	public String queryRadio_InStation_ByMonth() {
		//	站点的id编号;
		String 	  	stationid	 = "",sql="",result=null;
		//	结果标记内容;
		String[]  	results  	 = {"1","NO"};
		
		//	其中的表示内容:
		//	year:请求查询的年份内容;
		//	size:结果集合的数据内容;
		//	monthsize:查询出的月的条目结果;
		int 	    year		 = 0,size  	 = 0,monthsize=0,	monthcurrent = 0,	dis	=	0;
		//	获得月份的临时存储结构;
		JSONArray 	monthtemp	 = null;
		//	进行数据的承装内容结构;
		JSONArray 	arrayNew 	 = new JSONArray();
		
		//	站点的内容;
		stationid		   = util_Net.getRequest().getParameter("stationid");
		
		//	获得的年份;
		year	 		   = Integer.parseInt(util_Net.getRequest().getParameter("year"));

		//	查询出当前年份的可用月数个数;
		String 		sql_queryMonth  = "select month from stationstatistics_carandoil where stationid='"+stationid+"' and year="+year+" group by month ORDER BY datetime1 DESC";
		
		monthtemp					= super.select(sql_queryMonth);
		monthsize					= monthtemp.size();

		if(monthsize!=0)
			
			//	进行数据的转换;
			monthcurrent			= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));
		else {
			//	当结果不存在进行年份-1,进行上1年的检测;
			year					= year-1;
			//	并且再次进行数据的查询;
			sql_queryMonth  		= "select month from stationstatistics_carandoil where stationid='"+stationid+"' and year="+year+" group by month ORDER BY datetime1 DESC";
			monthtemp				= super.select(sql_queryMonth);
			monthsize				= monthtemp.size();
			
			//	当上1年也没有就进行跳出，并且返回连续两年没有信息;
			if(monthsize!=0)
				
				//	进行数据的转换;
				monthcurrent		= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));				
			else {
				result				= year+"-"+(year+1)+"无数据";
				return util_Net.sendResult(results[0], results[1], size, result);
			}
		}

		//	上限与当前月份的差值;
		dis		=	12-monthcurrent;
		
		//	对于当前的月份进行侦测;
		try {
			
			//	对上一年的数据进行添加;
			if(dis>0) {
				
				//	上一年的索引内容;
				int last	=	year-1;
				for(int i=12-dis;i<=12;i++) {
					sql		 		  = "select out_num,in_num,in_oil_quantity,in_oil_count,month from stationstatistics_carandoil where stationid='"+stationid+"' and year="+last+" and month="+i+"  order by datetime1";
					JSONArray array	  = super.select(sql);
					int 	  out_temp		= 0;
					int 	  in_temp 		= 0;
					double	  quantity_temp = 0;
					int	  	  count_temp 	= 0;
					for(int j=0;j<array.size();j++) {
						//	JSONOBject对象;
						JSONObject 	jo 		= array.getJSONObject(j);
						String 		out_num	= jo.getString("out_num");
						String 		in_num 	= jo.getString("in_num");
						String oil_quantity	= jo.getString("in_oil_quantity");
						String oil_count 	= jo.getString("in_oil_count");
						
						int    out	  = Integer.parseInt(out_num);
						int    in	  = Integer.parseInt(in_num);
						double quantity	  = Double.parseDouble(oil_quantity);
						int    count	  = Integer.parseInt(oil_count);
						out_temp	 += out;
						in_temp      += in;
						quantity_temp+= quantity;
						count_temp   += count;
					}
					JSONObject joNew  = new JSONObject();
					joNew.put("year", last);
					joNew.put("month", i);
					joNew.put("in", in_temp);
					joNew.put("out", out_temp);
					joNew.put("quantity", quantity_temp);
					joNew.put("count", count_temp);
					arrayNew.add(joNew);
				}
			}
			
			//	本年的数据的容器添加;
			for(int i=1;i<=monthcurrent;i++) {
				sql		 		  = "select out_num,in_num,in_oil_quantity,in_oil_count,month from stationstatistics_carandoil where stationid='"+stationid+"' and year="+year+" and month="+i+"  order by datetime1";
				JSONArray array	  = super.select(sql);
				int 	  out_temp		= 0;
				int 	  in_temp 		= 0;
				double	  quantity_temp = 0;
				int	  	  count_temp 	= 0;
				for(int j=0;j<array.size();j++) {
					//	JSONOBject对象;
					JSONObject 	jo 		= array.getJSONObject(j);
					String 		out_num	= jo.getString("out_num");
					String 		in_num 	= jo.getString("in_num");
					String oil_quantity	= jo.getString("in_oil_quantity");
					String oil_count 	= jo.getString("in_oil_count");
					
					int    out	  = Integer.parseInt(out_num);
					int    in	  = Integer.parseInt(in_num);
					double quantity	  = Double.parseDouble(oil_quantity);
					int    count	  = Integer.parseInt(oil_count);
					out_temp	 += out;
					in_temp      += in;
					quantity_temp+= quantity;
					count_temp   += count;
				}
				JSONObject joNew  = new JSONObject();
				joNew.put("year", year);
				joNew.put("month", i);
				joNew.put("in", in_temp);
				joNew.put("out", out_temp);
				joNew.put("quantity", quantity_temp);
				joNew.put("count", count_temp);
				arrayNew.add(joNew);
			}
			
			//	结果集合放回到数据集合当中;
			result	=	arrayNew.toString();	
			} catch (Exception e) {
				result=null;
			}

		//	判断结果内容;
		if(result!=null) {
			results[0]="0";
			results[1]="OK";
			size=arrayNew.size();
			
		}else {
			results[0]="1";
			results[1]="NO";
			size=0;
		}
		//	关闭数据库;
		close();
		//	数据结果的显示;
		return util_Net.sendResult(results[0], results[1], size, result);
	}
	
	//	8.按照牌照的次数进行调查-车牌进站率的比较;
	public String query_licence() {
		//	站点的id编号;
		String 	  stationid= "", min=null, max=null,result	=	null;
		String[]  results  = {"1","NO"};
		int 	  size	   = 0 ,	type	=0  , year=0,	monthsize=0,  monthcurrent=0,zheng=0,yu=0, lastStart=0 ,currentStart=0;
		//	最小的字符串;
		long	  mintime  = 0,maxtime=0;
		
		//	获得月份的临时存储结构;
		JSONArray 	monthtemp	 = null;
		//	结果集合的标签;
		JSONArray array	=	new JSONArray();
		
		//	站点的内容;
		stationid		=	util_Net.getRequest().getParameter("stationid");
		//	获得的年份;
		year	 		=	Integer.parseInt(util_Net.getRequest().getParameter("year"));
		//	进行排名的统计;
		type	 		=	Integer.parseInt(util_Net.getRequest().getParameter("type"));
		
		//	将当前的日期转换为字符内容;
		min				=	year+"-01-01 00:00:00";
		max				=	(year+1)+"-01-01 00:00:00";
		mintime			=	transDateStr2Long(min, Util.TAG_DATETIME);
		maxtime			=	transDateStr2Long(max, Util.TAG_DATETIME);

		//	查询出当前年份的可用月数个数;
		String 		sql_querySet  = "select datetime from monitorinfo where stationid='"+stationid+"' and datetime1 between "+mintime+" and "+maxtime+" order by datetime1 desc limit 0,1";
		//	结果的查询内容;
		monthtemp				  = super.select(sql_querySet);
		//	查询结果的返回数据集合;
		monthsize				  = monthtemp.size();
		
		String 		temp		  = null;
		
		//	当数据集合中具有结果的时候;
		if(monthsize!=0) {
			temp				  =	monthtemp.getJSONObject(0).getString("datetime");
			temp				  = temp.substring(temp.indexOf("-")+"-".length(), temp.indexOf("-")+"-".length()+2);
			
			//	进行数据的显示;
			monthcurrent		  = Integer.parseInt(temp);
			
		}else {
			
			//	当结果不存在进行年份-1,进行上1年的检测;
			year				  = year-1;
			
			//	将当前的日期转换为字符内容;
			min					  =	year+"-01-01 00:00:00";
			max					  =	(year+1)+"-01-01 00:00:00";
			mintime				  =	transDateStr2Long(min, Util.TAG_DATETIME);
			maxtime				  =	transDateStr2Long(max, Util.TAG_DATETIME);

			//	查询出当前年份的可用月数个数;
			sql_querySet  		  = "select datetime from monitorinfo where stationid='"+stationid+"' and datetime1 between "+mintime+" and "+maxtime+" order by datetime1 desc limit 0,1";
			//	结果的查询内容;
			monthtemp			  = super.select(sql_querySet);
			//	查询结果的返回数据集合;
			monthsize			  = monthtemp.size();
			
			//	当上1年也没有就进行跳出，并且返回连续两年没有信息;
			if(monthsize!=0) {
				
				temp			  =	monthtemp.getJSONObject(0).getString("datetime");
				temp			  = temp.substring(temp.indexOf("-")+"-".length(), temp.indexOf("-")+"-".length()+2);
				
				//	进行数据的显示;
				monthcurrent	  = Integer.parseInt(temp);
			}else {
				result			  = year+"-"+(year+1)+"无数据";
				return util_Net.sendResult(results[0], results[1], size, result);
			}	
		}

		//	数据的上限;
		int dis		=	12-monthcurrent;
		
		switch (type) {
		//	根据月排名
		case 0:
			
			//	两者之间具有差值;
			if(dis>0) {
				
				//	上一年的索引内容;
				int last	=	year-1;
				for(int i=12-dis;i<=12;i++) {
					
					int    j	= i+1;
					String start= last+"-"+i+"-1 00:00:00";
					String end  = last+"-"+j+"-1 00:00:00";
					int	   month= i;
					
					//	当月份超出则变成下一年;
					if(j==13) {
						int t2	= last+1;
						end		= t2+"-1-1 00:00:00";
					}
					
					//	进行相应的时间统计;
					//	开始标签;
					long lstart = transDateStr2Long(start, Util.TAG_DATETIME);
					//	结束标签;
					long lend	= transDateStr2Long(end, Util.TAG_DATETIME);
					
					//	按顺序进行排列;
					array.add(query_licenceByOrder(stationid,last+"","第"+month+"月",lstart, lend));
					
				}
				
			}
			
			//	根据12个月进行相应的数据信息;
			for(int i=1;i<=monthcurrent;i++) {
				
				int    j	= i+1;
				String start= year+"-"+i+"-1 00:00:00";
				String end  = year+"-"+j+"-1 00:00:00";
				int	   month= i;
				
				//	当月份超出则变成下一年;
				if(j==13) {
					int t2	=	year+1;
					end		=	t2+"-1-1 00:00:00";
				}
				
				//	进行相应的时间统计;
				//	开始标签;
				long lstart = transDateStr2Long(start, Util.TAG_DATETIME);
				//	结束标签;
				long lend	= transDateStr2Long(end, Util.TAG_DATETIME);
				
				//	按顺序进行排列;
				array.add(query_licenceByOrder(stationid,year+"","第"+month+"月",lstart, lend));
			}
					
			break;
		
		//	根据季度排名
		case 1:
			
			//	当具有差值的情况下;
			if(dis>0) {
				//	上1年;
				int last		=	year-1;
				
				zheng			=	(12-dis)/3;
				yu				=	(12-dis)%3;
				
				//	季度的开始;
				lastStart		=	zheng;
				
				if(yu>0)
					lastStart  +=	1;
				
				for(int i=lastStart;i<=4;i++) {
					
					int    j	  = i+1;
					int    month_i= (i-1)*3+1;
					int    month_j= (j-1)*3+1;
					
					String start  = last+"-"+month_i+"-1 00:00:00";
					String end    = last+"-"+month_j+"-1 00:00:00";
					
					
					if(month_j==13) {
						int t2	  = last+1;
						end		  = t2+"-1-1 00:00:00";
					}
//					System.out.println(start+" "+end);
					//	行相应的时间统计;
					//	开始标签;
					long lstart = transDateStr2Long(start, Util.TAG_DATETIME);
					//	结束标签;
					long lend	= transDateStr2Long(end, Util.TAG_DATETIME);
					//	按顺序进行排列;
					array.add(query_licenceByOrder(stationid,last+"","第"+i+"季度",lstart, lend));					
				}
				
			}
			// 本年;
			zheng			=	(monthcurrent)/3;
			yu				=	(monthcurrent)%3;
			currentStart	=	zheng;
			if(yu>0)
			  currentStart +=	1;

			for(int i=1;i<=currentStart;i++) {
				int    j	  = i+1;
				int    month_i= (i-1)*3+1;
				int    month_j= (j-1)*3+1;
				
				String start  = year+"-"+month_i+"-1 00:00:00";
				String end    = year+"-"+month_j+"-1 00:00:00";
				
				if(month_j==13) {
					int t2	  = year+1;
					end		  = t2+"-1-1 00:00:00";
				}
//				System.out.println(start+" "+end);
				//	行相应的时间统计;
				//	开始标签;
				long lstart = transDateStr2Long(start, Util.TAG_DATETIME);
				//	结束标签;
				long lend	= transDateStr2Long(end, Util.TAG_DATETIME);
				//	按顺序进行排列;
				array.add(query_licenceByOrder(stationid,year+"","第"+i+"季度",lstart, lend));
			}
			
			break;
			
		//	根据半年排名
		case 2:
			
			//	当具有差值的情况下;
			if(dis>0) {
				//	上1年;
				int last		=	year-1;
				
				zheng			=	(12-dis)/6;
				yu				=	(12-dis)%6;
				
				//	季度的开始;
				lastStart		=	zheng;
				
				if(yu>0)
					lastStart  +=	1;
				
				for(int i=lastStart;i<=2;i++) {
					
					int    j	  = i+1;
					int    month_i= (i-1)*6+1;
					int    month_j= (j-1)*6+1;
					
					String start  = last+"-"+month_i+"-1 00:00:00";
					String end    = last+"-"+month_j+"-1 00:00:00";
					
					
					if(month_j==13) {
						int t2	  = last+1;
						end		  = t2+"-1-1 00:00:00";
					}
//					System.out.println(start+" "+end);
					//	行相应的时间统计;
					//	开始标签;
					long lstart = transDateStr2Long(start, Util.TAG_DATETIME);
					//	结束标签;
					long lend	= transDateStr2Long(end, Util.TAG_DATETIME);
					//	按顺序进行排列;
					array.add(query_licenceByOrder(stationid,last+"","第"+i+"半年",lstart, lend));					
				}
				
			}
			// 本年;
			zheng			=	(monthcurrent)/6;
			yu				=	(monthcurrent)%6;
			currentStart	=	zheng;
			
			if(yu>0)
			  currentStart +=	1;

			for(int i=1;i<=currentStart;i++) {
				int    j	  = i+1;
				int    month_i= (i-1)*6+1;
				int    month_j= (j-1)*6+1;
				
				String start  = year+"-"+month_i+"-1 00:00:00";
				String end    = year+"-"+month_j+"-1 00:00:00";
				
				
				if(month_j==13) {
					int t2	  = year+1;
					end		  = t2+"-1-1 00:00:00";
				}
				System.out.println(start+" "+end);
				//	行相应的时间统计;
				//	开始标签;
				long lstart = transDateStr2Long(start, Util.TAG_DATETIME);
				//	结束标签;
				long lend	= transDateStr2Long(end, Util.TAG_DATETIME);
				//	按顺序进行排列;
				array.add(query_licenceByOrder(stationid,year+"","第"+i+"半年",lstart, lend));
			}
					
			break;

		default:
			break;
		}
		
		if(array!=null) {
			results[0]= "0";
			results[1]= "OK";
			result	  = array.toString();
			size	  = array.size();
		}
		
		return util_Net.sendResult(results[0], results[1], size, result);
	}
	
	//	9.按照各类用户的加油次数的变化趋势;
	public String query_addOilCountByDifferentKind() {
		//	站点的编号;
		String   stationid= null,result=null,sql="";
		String[] results  = {"1","NO"};
		int 	 year	  = 0,	size=0,	monthsize	=	0,	monthcurrent	=	0,dis=0;
		
		//	获得月份的临时存储结构;
		JSONArray 	monthtemp	 = null;

		//	结果的列表;
		JSONArray array2  =	new JSONArray();
		
		//	站点的内容;
		stationid		  =	util_Net.getRequest().getParameter("stationid");
		
		//	获得的年份;
		year	 		  =	Integer.parseInt(util_Net.getRequest().getParameter("year"));

		//	查询出当前年份的可用月数个数;
		String 		sql_queryMonth  = "select month from car_addoil where stationid='"+stationid+"' and year="+year+" group by month ORDER BY datetime1 DESC";
		
		monthtemp					= super.select(sql_queryMonth);
		monthsize					= monthtemp.size();
		
		if(monthsize!=0)
			
			//	进行数据的转换;
			monthcurrent			= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));
		else {
			//	当结果不存在进行年份-1,进行上1年的检测;
			year					= year-1;
			//	并且再次进行数据的查询;
			sql_queryMonth  		= "select month from car_addoil where stationid='"+stationid+"' and year="+year+" group by month ORDER BY datetime1 DESC";
			monthtemp				= super.select(sql_queryMonth);
			monthsize				= monthtemp.size();
			
			//	当上1年也没有就进行跳出，并且返回连续两年没有信息;
			if(monthsize!=0)
				
				//	进行数据的转换;
				monthcurrent		= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));				
			else {
				result				= year+"-"+(year+1)+"无数据";
				return util_Net.sendResult(results[0], results[1], size, result);
			}
		}
		
		//	上限与当前月份的差值;
		dis		=	12-monthcurrent;
		System.out.println("当前月份="+monthcurrent);
		if(dis>0) {
			int last=year-1;
			//	进行按月进行统计的内容;
			for(int i=12-dis;i<=12;i++) {
				
				//	进行相应的数据传输;
				//	进行相应的类别的内容信息;
				JSONArray ary=new JSONArray();
				for(int type=1;type<=2;type++) {
					sql		    		 =	"select carid,in_oil_quantity,type from car_addoil where stationid='"+stationid+"' and year="+last+" and month="+i+" and type="+type;
					
					JSONArray array		 =	super.select(sql);
					
					int 	count_temp 	 = 0;
					double	quantity_temp= 0;
					if(array.size()>0) {
						
						//	对象相应的数据进行总结;
						for(int j=0;j<array.size();j++) {
							//	每个对象的内容;
							JSONObject 		o		= 	array.getJSONObject(j);
							//	加油的内容;
							String in_oil_quantity	= 	o.getString("in_oil_quantity");
							double in_quantity		=	Double.parseDouble(in_oil_quantity);
							//	进行量统计;
							quantity_temp		   +=	in_quantity;
							
							//	进行数统计;
							count_temp++;
							
						}
					}
					JSONObject obj=new JSONObject();
					obj.put("type", type);
					obj.put("count", count_temp);
					obj.put("quantity", quantity_temp);
					ary.add(obj);
				}
				JSONObject object=new JSONObject();
				object.put("stationid", stationid);
				object.put("year", last);
				object.put("month", i);
				object.put("data", ary.toString());
				array2.add(object);	
			}
		}
		//	进行按月进行统计的内容;
		for(int i=1;i<=monthcurrent;i++) {
			
			//	进行相应的数据传输;
			//	进行相应的类别的内容信息;
			JSONArray ary=new JSONArray();
			for(int type=1;type<=2;type++) {
				sql		    		 =	"select carid,in_oil_quantity,type from car_addoil where stationid='"+stationid+"' and year="+year+" and month="+i+" and type="+type;
				
				JSONArray array		 =	super.select(sql);
				
				int 	count_temp 	 = 0;
				double	quantity_temp= 0;
				
				if(array.size()>0) {
				
					//	对象相应的数据进行总结;
					for(int j=0;j<array.size();j++) {
						//	每个对象的内容;
						JSONObject 		o		= 	array.getJSONObject(j);
						//	加油的内容;
						String in_oil_quantity	= 	o.getString("in_oil_quantity");
						double in_quantity		=	Double.parseDouble(in_oil_quantity);
						//	进行量统计;
						quantity_temp		   +=	in_quantity;
						
						//	进行数统计;
						count_temp++;
						
					}	
				}
				JSONObject obj=new JSONObject();
				obj.put("type", type);
				obj.put("count", count_temp);
				obj.put("quantity", quantity_temp);
				ary.add(obj);
							
			}
			
			JSONObject object=new JSONObject();
			object.put("stationid", stationid);
			object.put("year", year);
			object.put("month", i);
			object.put("data", ary.toString());
			array2.add(object);	
		}
		
		if(array2!=null) {
			results[0]= "0";
			results[1]= "OK";
			result	  = array2.toString();
			size=array2.size();
		}
		
		return util_Net.sendResult(results[0], results[1], size, result);
	}

	//	10.按照月进行——人脸与车辆比
	public String query_RadioByHumanAndCar() {
		String[]  results= {"1","NO"};
		String    result = null,stationid=null;
		JSONArray array	 = new JSONArray();

		//	其中的表示内容:
		//	year:请求查询的年份内容;
		//	size:结果集合的数据内容;
		//	monthsize:查询出的月的条目结果;
		int 	    year		 = 0,size  	 = 0,monthsize=0,	monthcurrent = 0,	dis	=	0;
		//	获得月份的临时存储结构;
		JSONArray 	monthtemp	 = null;
		//	进行数据的承装内容结构;
		
		//	站点的内容;
		stationid		   = util_Net.getRequest().getParameter("stationid");
		
		//	获得的年份;
		year	 		   = Integer.parseInt(util_Net.getRequest().getParameter("year"));

		//	查询出当前年份的可用月数个数;
		String 		sql_queryMonth  = "select month from stationstatistics_carandoil where year="+year+" and stationid='"+stationid+"' order by datetime1 desc limit 0,1";
		
		monthtemp					= super.select(sql_queryMonth);
		monthsize					= monthtemp.size();

		if(monthsize!=0)
			
			//	进行数据的转换;
			monthcurrent			= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));
		else {
			//	当结果不存在进行年份-1,进行上1年的检测;
			year					= year-1;
			//	并且再次进行数据的查询;
			sql_queryMonth  		= "select month from stationstatistics_carandoil where year="+year+" and stationid='"+stationid+"' order by datetime1 desc limit 0,1";
			monthtemp				= super.select(sql_queryMonth);
			monthsize				= monthtemp.size();
			
			//	当上1年也没有就进行跳出，并且返回连续两年没有信息;
			if(monthsize!=0)
				
				//	进行数据的转换;
				monthcurrent		= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));				
			else {
				result				= year+"-"+(year+1)+"无数据";
				return util_Net.sendResult(results[0], results[1], size, result);
			}
		}

		//	上限与当前月份的差值;
		dis		=	12-monthcurrent;		
		
		if(dis>0) {
			
			//	上一年的索引内容;
			int last	=	year-1;
			for(int i=12-dis;i<=12;i++) {
				
				String 	  	sql		 = "select a.out_num,a.in_num,b.in_human_num from stationstatistics_carandoil a, stationstatistics_human b where a.stationid=b.stationid and a.year=b.year and a.month=b.month and a.day=b.day and a.year="+last+" and a.month="+i+" and a.stationid='"+stationid+"'";
				
				JSONArray 	array2	 = super.select(sql);
				int car_out_num_temp = 0;
				int car_in_num_temp  = 0;
				int human_in_num_temp= 0;

				System.out.println(array2.toString());
				//	进行数据的传入内容;
				for(int j=0;j<array2.size();j++) {
					
					JSONObject     obj = array2.getJSONObject(j);
					//	临街车辆数;
					String car_out_num = obj.getString("out_num");
					//	入站车辆数;
					String car_in_num  = obj.getString("in_num");
					//	入点人脸数;
					String human_in_num= obj.getString("in_human_num");
					
					int	   		car_out= Integer.parseInt(car_out_num);
					int	   		car_in = Integer.parseInt(car_in_num);
					int	   	   human_in= Integer.parseInt(human_in_num);
					
					car_out_num_temp  += car_out;
					car_in_num_temp   += car_in;
					human_in_num_temp += human_in;

				}
				JSONObject   object= new JSONObject();
				
				object.put("year", last);
				object.put("month", i);
				object.put("car_out_num", car_out_num_temp);
				object.put("car_in_num", car_in_num_temp);
				object.put("human_in_num", human_in_num_temp);
				
				array.add(object);
			}
		}
		
		//	进行12个月的查询;
		for(int i=1;i<=monthcurrent;i++) {
			String 	  	sql		 = "select a.out_num,a.in_num,b.in_human_num from stationstatistics_carandoil a, stationstatistics_human b where a.stationid=b.stationid and a.year=b.year and a.month=b.month and a.day=b.day and a.year="+year+" and a.month="+i+" and a.stationid='"+stationid+"'";
			
			JSONArray 	array2	 = super.select(sql);
			int car_out_num_temp = 0;
			int car_in_num_temp  = 0;
			int human_in_num_temp= 0;

			System.out.println(array2.toString());
			//	进行数据的传入内容;
			for(int j=0;j<array2.size();j++) {
				
				JSONObject     obj = array2.getJSONObject(j);
				//	临街车辆数;
				String car_out_num = obj.getString("out_num");
				//	入站车辆数;
				String car_in_num  = obj.getString("in_num");
				//	入点人脸数;
				String human_in_num= obj.getString("in_human_num");
				
				int	   		car_out= Integer.parseInt(car_out_num);
				int	   		car_in = Integer.parseInt(car_in_num);
				int	   	   human_in= Integer.parseInt(human_in_num);
				
				car_out_num_temp  += car_out;
				car_in_num_temp   += car_in;
				human_in_num_temp += human_in;

			}
			JSONObject   object= new JSONObject();
			
			object.put("year", year);
			object.put("month", i);
			object.put("car_out_num", car_out_num_temp);
			object.put("car_in_num", car_in_num_temp);
			object.put("human_in_num", human_in_num_temp);
			
			array.add(object);

		}
		
		//	进行相应的数据传输;
		if(array!=null){
			results[0]= "1";
			results[1]= "OK";
			size	  = array.size();
			result	  = array.toString();
		}
		
		return util_Net.sendResult(results[0], results[1], size, result);
	}
	
	//	11.根据月份查询入站人数;
	public String query_InStationByMonth() {
		String[]  results= {"1","NO"};
		String    result = null,stationid=null,sql="";
		JSONArray array	 = new JSONArray();
		int 	  size	 = 0, year	= 0,	monthsize=0,	monthcurrent = 0,	dis	=	0;
		
		//	获得月份的临时存储结构;
		JSONArray 	monthtemp	 = null;
		
		//	站点的内容;
		stationid		   = util_Net.getRequest().getParameter("stationid");
		
		//	获得的年份;
		year	 		   = Integer.parseInt(util_Net.getRequest().getParameter("year"));

		//	查询出当前年份的可用月数个数;
		String 		sql_queryMonth  = "select month from stationstatistics_carandoil where stationid='"+stationid+"' and year="+year+" group by month ORDER BY datetime1 DESC";
		
		monthtemp					= super.select(sql_queryMonth);
		monthsize					= monthtemp.size();

		if(monthsize!=0)
			
			//	进行数据的转换;
			monthcurrent			= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));
		else {
			//	当结果不存在进行年份-1,进行上1年的检测;
			year					= year-1;
			//	并且再次进行数据的查询;
			sql_queryMonth  		= "select month from stationstatistics_human where stationid='"+stationid+"' and year="+year+" group by month order by datetime1 desc limit 0,1";
			monthtemp				= super.select(sql_queryMonth);
			monthsize				= monthtemp.size();
			
			//	当上1年也没有就进行跳出，并且返回连续两年没有信息;
			if(monthsize!=0)
				
				//	进行数据的转换;
				monthcurrent		= Integer.parseInt(monthtemp.getJSONObject(0).getString("month"));				
			else {
				result				= year+"-"+(year+1)+"无数据";
				return util_Net.sendResult(results[0], results[1], size, result);
			}
		}

		//	上限与当前月份的差值;
		dis		=	12-monthcurrent;
	
		//	对上一年的数据进行添加;
		if(dis>0) {
			
			//	上一年的索引内容;
			int last	=	year-1;
			for(int i=12-dis;i<=12;i++) {
				
				sql	 			= "select in_human_num from stationstatistics_human where stationid='"+stationid+"' and year="+last+" and month="+i;
				JSONArray array2= super.select(sql);
				
				int in_num_temp = 0;
				for(int j=0;j<array2.size();j++) {
					JSONObject  obj			= array2.getJSONObject(j);
					String 		in_human_num= obj.getString("in_human_num");
					int			in_num		= Integer.parseInt(in_human_num);
					in_num_temp			   += in_num;
				}
				
				JSONObject 	object	=	new JSONObject();
				object.put("year", last);
				object.put("month", i);
				object.put("in_human_num", in_num_temp);
				
				array.add(object);
			
			}
		}
		
		for(int i=1;i<=monthcurrent;i++) {
			sql	 			= "select in_human_num from stationstatistics_human where stationid='"+stationid+"' and year="+year+" and month="+i;
			JSONArray array2= super.select(sql);
			
			int in_num_temp = 0;
			for(int j=0;j<array2.size();j++) {
				JSONObject  obj			= array2.getJSONObject(j);
				String 		in_human_num= obj.getString("in_human_num");
				int			in_num		= Integer.parseInt(in_human_num);
				in_num_temp			   += in_num;
			}
			
			JSONObject 	object	=	new JSONObject();
			object.put("year", year);
			object.put("month", i);
			object.put("in_human_num", in_num_temp);
			
			array.add(object);
		}
		
		//	进行相应的数据传输;
		if(array!=null){
			results[0]= "0";
			results[1]= "OK";
			size	  = array.size();
			result	  = array.toString();
		}
		
		return util_Net.sendResult(results[0], results[1], size, result);
	}
	
	public String query() {
		
		//	进行相应的数据类型;
		String      lcommand   = null, stationid  = null, deviceip = null,limitcount = null,	currentpage= null;
		String 		sqlall	   = "";
		String 		sql	  	   = "";
		int 		first	   = 1,nlimitcount=10;
		ArrayList<String> list=new ArrayList<String>();
		
		try {
			lcommand	 = util_Net.getRequest().getParameter("lcommand");	
			deviceip 	 = util_Net.getRequest().getParameter("deviceip");
			stationid 	 = util_Net.getRequest().getParameter("stationid");
			
			limitcount	 = util_Net.getRequest().getParameter("limit");
			currentpage	 = util_Net.getRequest().getParameter("page");

			System.out.println("操作正常");

		} catch (Exception e) {
			System.out.println("异常处理");

		}
		
		
		//	lcommand判断是否存在;
		if(lcommand!=null&&!lcommand.trim().equals("")) {
			list.add("lcommand='"+lcommand+"'");
		}
		
		//	stationid判断是否存在;
		if(stationid!=null&&!stationid.trim().equals("")) {
			list.add("stationid='"+stationid+"'");
		}
		//	deviceip判断是否存在;		
		if(deviceip!=null&&!deviceip.trim().equals("")) {
			list.add("deviceip='"+deviceip+"'");
		}
		//	currentpage判断是否存在;
		if(limitcount!=null&&!limitcount.trim().equals("")) {
			nlimitcount=Integer.parseInt(limitcount);
		}

		if(currentpage!=null&&!currentpage.trim().equals("")) {
			first	     = (Integer.parseInt(currentpage)-1)*nlimitcount;
		}

		String where="";
		if(list.size()!=0) {
			for(String item:list) {
				where+=" "+item+" and";
			}
			where="where"+where.subSequence(0, where.length()-"and".length());	
		}

		//	去除最后一个and;
		//	进行相应的查询内容;
		sql   = "select * from monitorinfo "+where+" order by datetime1 desc limit "+first+","+nlimitcount;
		sqlall= "select count(lcommand) from monitorinfo "+where+" order by datetime1 desc";

		//	TODO 数据库查询操作;
		JSONArray 	 array = select(sql);

		int 		 count = getQueryCount(sqlall);
		
		return util_Net.sendResult("0", "OK", count, array.toString());
	}
	
	@Override
	public JSONArray select(String sql) {

		JSONArray 				list		=	new  JSONArray();
		//	操作的主流程;
		setTag("查询数据");
		tagStartModule();

		try{
	    	 
		      Statement 		stmt		= (Statement)super.coon.createStatement();
	          // 数据的结果集合;
	          ResultSet 		rs			= (ResultSet)stmt.executeQuery(sql);
	          ResultSetMetaData rsmd 		= rs.getMetaData() ; 
	          int 				columnCount = rsmd.getColumnCount();
	          //	
	          while(rs.next()){
	        	  int 			  count		= 0;
	        	  JSONObject	  object	= new JSONObject();
	        	  while(count<columnCount) {
	        		  String key			= rsmd.getColumnName(count+1);
	        		  // 列数的内容;
	        		  String value			= rs.getString(key);
	        		  
	        		  // 将对应的文件格式进行替换;
	        		  if(key.contains(KEY_1)) {
	        			  System.out.println("文件"+value);	        			  
	        			  // 文件夹;
	        			  String folder= FOLDER.replace(TAG_2, "/");
	        			  
	        			  String head  = this.util_Net.getHttphead()+"/ExtractLoadV01/"+folder;

	        			  value		   = value.replace(TAG_1, "/");
	        			  
	        			  if(!value.contains("无图像保存")) {
	        				  value	   = value.substring(value.indexOf("/"), value.length());
		        			  value	   = head+value;	
	        				  System.out.println(value);  
	        			  }
	        		  }
	        		  
	        		  // 参数的添加;
	        		  object.put(key, value);
	        		  count++;
	        	  }
	        	  list.add(object);
	         }
	          stmt.close();
	     }catch(Exception e){
	        e.printStackTrace();
	     }
		    
	    tagEndModule();
	    tagComputeProcessingTime();
	    
        return list;
	}

	//	获得所有的数据条数;
	@Override
	public int getQueryCount(String sql) {

		return super.getQueryCount(sql);
	}
	//	修改相应的数据内容;
	@Override
	public int update(String sql) {
		
		return super.update(sql);
	}
}
