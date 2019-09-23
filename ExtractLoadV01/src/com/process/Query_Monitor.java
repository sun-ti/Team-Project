package com.process;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

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
		
	}

	public String query(String lcommand,String stationid,String deviceip,String currentpage,String limitcount) {
		String 		sqlall	   = "";
		String 		sql	  	   = "";
		int 		first	   = 1,nlimitcount=10;
		ArrayList<String> list=new ArrayList<String>();
		
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
		
		return util_Net.sendResult("200", "OK", count, array.toString());
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
