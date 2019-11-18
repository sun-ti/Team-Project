package com.free.dc.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class DBaseUtils extends Util{
	//	数据库操作的链的内容;
	private Connection coon = null;

	// 构造函数;
	public DBaseUtils() {
		try {
			// 构造函数的驱动类;
			Class.forName(Util.DB_DRIVER);
			coon = (Connection) DriverManager.getConnection(Util.DB_url, Util.DB_user, Util.DB_password);
			if (!coon.isClosed()) {
				System.out.println("数据库的连接!");
			}
			else {
				System.out.println("数据库连接失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
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
		
		tagEndStartModule();
		tagComputeProcessingTime();
		
		return oper;
	}

	// 数据的查询结果的内容;
	public ArrayList<String[]> select(String sql){
		ArrayList<String[]> list		= new ArrayList<String[]>();
		
		setTag("数据查询");
		tagStartModule();

	    try{
	    	 
	      Statement stmt=(Statement)this.coon.createStatement();
          // 数据的结果集合;
          ResultSet rs=(ResultSet)stmt.executeQuery(sql);
          
          ResultSetMetaData rsmd 		= rs.getMetaData() ; 
          int 				columnCount = rsmd.getColumnCount();
          
          System.out.println("数据的列数:"+columnCount);
         
          while(rs.next()){
        	  int 			  count		= 0;
        	  String[] 		  cols		= new String[columnCount];
        	  while(count<columnCount) {
        		  // 列数的内容;
        		  String pameter		=rs.getString((count+1)).toString();
        		  // 参数的添加;
        		  cols[count]			= pameter;
        		  count++;
        	  }
        	  list.add(cols);
         }
          stmt.close();
	     }catch(Exception e){
	        e.printStackTrace();
	     }
	    
	    tagEndStartModule();
	    tagComputeProcessingTime();
        
        return list;
   }
}
