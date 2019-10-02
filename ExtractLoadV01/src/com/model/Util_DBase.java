package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import com.models.Utils_DBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Util_DBase extends Util implements Utils_DBase{
	// 数据库的驱动的类;
	public String 				driver   = "com.mysql.jdbc.Driver";
	// 数据库的驱动链接;
	//
	public String 				url 	 = null;
	public Connection  			coon 	 = null;

	// 构造函数;
	public Util_DBase() {
		
	}
	
	public void LinkDatabase(Util_Net util_Net) {
		//	进行
		try {
			//	初始化工具
			Util_Init util_Init	=	new Util_Init(util_Net);
			this.url			=	"jdbc:mysql://"+util_Init.getHTTP_IP()+":"+util_Init.getDB_PORT()+"/"+util_Init.getDB_NAME()+"?useUnicode=true&characterEncoding="+util_Init.getDB_ENCODING();
			System.out.println(this.url);
			//  构造函数的驱动类;
			Class.forName(driver);
			this.coon = (Connection) DriverManager.getConnection(url, util_Init.getDB_USERNAME(), util_Init.getDB_PASSWORD());
			if (!this.coon.isClosed()) {
				System.out.println("数据库的连接!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 数据库的关闭;
	public void close() {
		try {
			this.coon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 数据的更新;
	public int update(String sql) {
		int oper=0;
		PreparedStatement preStmt=null;
		setTag("数据更新");
		tagStartModule();
		
		try {
			preStmt = (PreparedStatement) this.coon.prepareStatement(sql);
			
			//	数据的操作的执行内容;
			oper	=	preStmt.executeUpdate();
			preStmt.close();

		} catch (Exception e) {

			oper	=	0;
		}
		
		tagEndModule();
		tagComputeProcessingTime();
		
		return oper;
	}
	
	//	进行相应的数据查询
	public JSONArray select(String sql) {
		JSONArray 				list		=	new  JSONArray();
		
//		setTag("查询数据");
//		tagStartModule();
		
		try{
	    	 
		      Statement 		stmt		= (Statement)this.coon.createStatement();
	          // 数据的结果集合;
	          ResultSet 		rs			= (ResultSet)stmt.executeQuery(sql);
	          ResultSetMetaData rsmd 		= rs.getMetaData() ; 
	          int 				columnCount = rsmd.getColumnCount();
	         
	          while(rs.next()){
	        	  int 			  count		= 0;
	        	  JSONObject	  object	= new JSONObject();
	        	  while(count<columnCount) {
	        		  String key			= rsmd.getColumnName(count+1);
	        		  // 列数的内容;
	        		  String value			= rs.getString(key);
	        		  
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

//		tagEndModule();
//		tagComputeProcessingTime();
		
		return list;
	}

	@Override
	public int getQueryCount(String sql) {
		
		ArrayList<String> list=new ArrayList<String>();
		try {

			Statement stmt = (Statement) this.coon.createStatement();
			// 数据的结果集合;
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);

			while (rs.next()) {
				
				 String pameter		= rs.getString(1).toString();
				 //	结果内容;
				 list.add(pameter);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	          
		return Integer.valueOf(list.get(0));
	}
	//	相应的参数;
	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public Connection getCoon() {
		return coon;
	}

}
