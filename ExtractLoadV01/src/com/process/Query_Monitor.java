package com.process;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

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

	//	根据车辆牌照进行查询
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
			// TODO: handle exception
		}

		String between	 = "";
		if(start!=null&&!start.equals("")&&end!=null&&!end.equals("")) {

			lstart		 = transDateStr2Long(start, "yyyy-MM-dd hh:mm:ss");
			lend		 = transDateStr2Long(end, "yyyy-MM-dd hh:mm:ss");
			
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
		//	信息查询;
		sql	  			= "select a.note,b.stationid,b.deviceip,b.errMsg,b.datetime,b.datetime1,b.pic1,b.pic2 from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip "+where+" and a.kind=1 "+between+" order by b.datetime1 desc limit "+first+","+nlimitcount;
		JSONArray array = select(sql);

		//	查询总页数;
		sqlAll			= "select count(b._id) from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip "+where+" and a.kind=1 "+between;
		
		int 	  count = getQueryCount(sqlAll);
		
		return util_Net.sendResult("0", "OK", count, array.toString());
	}
	
	//	根据车辆流量进行查询-按日:eg:2019-09-12;
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
			// TODO: handle exception
		}
		
		//	判断输入的时间是否完整;
		if(date!=null&&!date.equals("")) {
			//	当时间信息符合格式时;
			if(checkDate(date, "yyyy-MM-dd")) {
				//	初始的时间;
				start= date+" 00:00:00";
				lstart=transDateStr2Long(start, "yyyy-MM-dd hh:mm:ss");
				
				//	结束的时间;
				end	 = date+" 23:59:59";
				lend=transDateStr2Long(end, "yyyy-MM-dd hh:mm:ss");
				
			}
			//	当时间信息不符合格式时;
			else
			return util_Net.sendResult("0", "NO", 0, null);
		}
		//	当时间信息输入为错误时;
		else
		return util_Net.sendResult("0", "NO", 0, null);
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
	
	//	根据车辆流量进行查询-按月;
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
			// TODO: handle exception
		}

		//	判断输入的时间是否完整;
		if(date!=null&&!date.equals("")&&date.length()==4) {
			int nTemp=0;
			try {
				nTemp=Integer.parseInt(date);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(nTemp>999&&nTemp<2999) {
				//	参数重新进行赋值;
				nDate=nTemp;
			}//	当时间信息输入为错误时;
			else
				return util_Net.sendResult("0", "NO", 0, null);
			
		}
		//	当时间信息输入为错误时;
		else
		return util_Net.sendResult("0", "NO", 0, null);
		
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
			
			long lstart=transDateStr2Long(start, "yyyy-MM-dd hh:mm:ss");
			long lend  =transDateStr2Long(end, "yyyy-MM-dd hh:mm:ss");
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
	
	//	根据人脸识别进行查询;
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
			// TODO: handle exception
		}
		
		String between	 = "";
		if(start!=null&&!start.equals("")&&end!=null&&!end.equals("")) {

			lstart		 = transDateStr2Long(start, "yyyy-MM-dd hh:mm:ss");
			lend		 = transDateStr2Long(end, "yyyy-MM-dd hh:mm:ss");
			
			between		 = "and b.datetime1 between "+lstart+" and "+lend;
		}

		//	currentpage判断是否存在;
		if(limitcount!=null&&!limitcount.trim().equals("")) {
			nlimitcount=Integer.parseInt(limitcount);
		}

		if(currentpage!=null&&!currentpage.trim().equals("")) {
			first	     = (Integer.parseInt(currentpage)-1)*nlimitcount;
		}
		
		//	信息查询;
		sql	  			= "select a.note,b.stationid,b.deviceip,b.errMsg,b.datetime,b.datetime1,b.pic1,b.pic2 from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip and a.stationid='"+stationid+"' and a.kind=3 "+between+" order by b.datetime1 desc limit "+first+","+nlimitcount;
		JSONArray array = select(sql);
		
		//	查询总页数;
		sqlAll			= "select count(b._id) from device a, monitorinfo b where a.stationid=b.stationid and a.deviceip=b.deviceip and a.stationid='"+stationid+"' and a.kind=3 "+between;
		int 	  count = getQueryCount(sqlAll);
		
		return util_Net.sendResult("0", "OK", count, array.toString());
	}
	
	//	根据客户流量进行查询-按日;
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
			// TODO: handle exception
		}

		//	判断输入的时间是否完整;
		if(date!=null&&!date.equals("")) {
			//	当时间信息符合格式时;
			if(checkDate(date, "yyyy-MM-dd")) {
				//	初始的时间;
				start= date+" 00:00:00";
				lstart=transDateStr2Long(start, "yyyy-MM-dd hh:mm:ss");
				
				//	结束的时间;
				end	 = date+" 23:59:59";
				lend=transDateStr2Long(end, "yyyy-MM-dd hh:mm:ss");
			}
			//	当时间信息不符合格式时;
			else
			return util_Net.sendResult("0", "NO", 0, null);
		}
		//	当时间信息输入为错误时;
		else
		return util_Net.sendResult("0", "NO", 0, null);
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
	//	根据客户流量进行查询-按月;
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
			// TODO: handle exception
		}

		//	判断输入的时间是否完整;
		if(date!=null&&!date.equals("")&&date.length()==4) {
			int nTemp=0;
			try {
				nTemp=Integer.parseInt(date);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(nTemp>999&&nTemp<2999) {
				//	参数重新进行赋值;
				nDate=nTemp;
			}//	当时间信息输入为错误时;
			else
				return util_Net.sendResult("0", "NO", 0, null);
			
		}
		//	当时间信息输入为错误时;
		else
		return util_Net.sendResult("0", "NO", 0, null);
		
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
			
			long lstart=transDateStr2Long(start, "yyyy-MM-dd hh:mm:ss");
			long lend  =transDateStr2Long(end, "yyyy-MM-dd hh:mm:ss");
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

		//	数据库查询操作;
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
	        			  // 文件夹;
	        			  String folder= FOLDER.replace(TAG_2, "/");
	        			  
	        			  String head  = this.util_Net.getHttphead()+"/"+folder;
	        			  
	        			  value		   = value.replace(TAG_1, "/").replace("[","_").replace("]", "");
	        			  value		   = value.substring(value.indexOf(":") + 1);
	        			  
	        			  value		   = head+value;
			  
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
