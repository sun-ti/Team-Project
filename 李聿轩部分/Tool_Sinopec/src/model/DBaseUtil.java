package model;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.DBaseUtils;

public class DBaseUtil extends Util implements DBaseUtils{

	//	读取文件的相应参数的配置;
	private final String CHARSET		= "utf-8";
	private final String TAG			= "\"";
	private final String TAG2			= "";
	
	//	数据库的接口的相应内容;	
	private Connection 	 coon 			= null;
	private int 		 mysql_version 	= 5;
	private String  	 driver 		= null;
	private String  	 server_ip	  	= null;
	private String 		 server_port	= null;
	private String  	 port		  	= null;
	private String  	 db_name       	= null;
	private String  	 charset	    = null;
	private String 		 url 			= null;
	private String 		 user			= null;
	private String 		 password  		= null;
	private String		 configPath		= null;
	
	// 构造函数;
	public DBaseUtil() {
		//	进行数据的初始化操作;
		super();
	}
	
	public boolean setParameters(String path) {
		boolean 	flag	=	false;
		
		//	进行相应的配置文件的数据的读取;
		File 		file	=	new File(path);
		String  	content = 	super.readFile(file, CHARSET);

		//	对文件进行读取的目标对象;
		JsonObject 	obj		=	null;
		
		//	当读取的内容不为空的情况下;
		if(!content.trim().equals("")&&content!=null) {
			
			try {
				//	将字符串转换为JSON格式的文件;
				obj					=	new JsonParser().parse(content).getAsJsonObject();
				String temp			=   obj.get("mysql_version").toString().replace(TAG,TAG2);
				
				//	获取相应的参数;
				this.mysql_version	=	Integer.valueOf(temp);
				this.server_ip	    =   obj.get("server_ip").toString().replace(TAG,TAG2);
				this.server_port	=   obj.get("server_port").toString().replace(TAG,TAG2);
				this.port			=	obj.get("db_port").toString().replace(TAG,TAG2);
				this.db_name		=	obj.get("db_name").toString().replace(TAG,TAG2);
				this.charset		=	obj.get("db_charset").toString().replace(TAG,TAG2);
				
				//	当MySQl的版本<8时;
				if(mysql_version<8) {
					driver		=	"com.mysql.jdbc.Driver";
					url 		= 	"jdbc:mysql://"+server_ip+":"+port+"/"+db_name+"?useUnicode=true&characterEncoding="+charset;
				}
				//	当MySQL的版本>=8时;
				else {
					driver 		= 	"com.mysql.cj.jdbc.Driver";
					url 		= 	"jdbc:mysql://"+server_ip+":"+port+"/"+db_name+"?useUnicode=true&characterEncoding="+charset+"&useSSL=false&serverTimezone=UTC";
				}
				
				this.user			=	obj.get("db_user_name").toString().replace(TAG,TAG2);
				this.password		=	obj.get("db_user_password").toString().replace(TAG,TAG2);
				this.configPath		=	path;
				flag			=	true;
			
			} catch (Exception e) {
				e.printStackTrace();
				//	结果的参数标志;
				flag		=	false;
			}			
		}
		//	数据结果的赋值;
		return flag;
	}

	//	数据库的开启操作;
	public void open() {
		try {
			// 构造函数的驱动类;
			Class.forName(driver);
			coon = (Connection) DriverManager.getConnection(url, user, password);
			if (!coon.isClosed()) {
				System.out.println("数据库的连接!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkOpen(String version,String server_ip,String server_port,String db_port,String db_name,String db_charset,String user,String pwd) {
		boolean 	flag	=	false;
		int    mysql_version=	0;
		
		//	进行相应的数据的链接;
		try {
			
			mysql_version		=	Integer.parseInt(version);
			//	当MySQl的版本<8时;
			if(mysql_version<8) {
				driver		=	"com.mysql.jdbc.Driver";
				url 		= 	"jdbc:mysql://"+server_ip+":"+port+"/"+db_name+"?useUnicode=true&characterEncoding="+charset;
			}
			//	当MySQL的版本>=8时;
			else {
				driver 		= 	"com.mysql.cj.jdbc.Driver";
				url 		= 	"jdbc:mysql://"+server_ip+":"+port+"/"+db_name+"?useUnicode=true&characterEncoding="+charset+"&useSSL=false&serverTimezone=UTC";
			}
			
			// 构造函数的驱动类;
			Class.forName(driver);
			coon = (Connection) DriverManager.getConnection(url, user, password);
			if (!coon.isClosed()) {
				flag		=	true;
			}
		} catch (Exception e) {
			flag	=	false;
		}

		return  flag;
	}

	public int getMysql_version() {
		return mysql_version;
	}

	public String getServer_port() {
		return server_port;
	}

	public String getPort() {
		return port;
	}

	public String getCharset() {
		return charset;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	//	数据库的参数部分;
	//	服务的ip地址;
	public String getServer_ip() {
		return server_ip;
	}

	//	数据库的名称;
	public String getDb_name() {
		return db_name;
	}

	//	配置参数;
	public String getConfigPath() {
		return configPath;
	}
	
	// 数据库的关闭操作;
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
		// SQL语句;
//		System.out.println(sql);
		try {
			preStmt = (PreparedStatement) this.coon.prepareStatement(sql);
			
			//	数据的操作的执行内容;
			oper	=	preStmt.executeUpdate();
			System.out.println("数据表更新完毕!");
			preStmt.close();

		} catch (Exception e) {

			oper	=	0;
		}
		
		return oper;
	}
	
	// 数据的查询结果的内容;
	public ArrayList<String[]> select(String sql){
		long 				time1		= System.currentTimeMillis(); 
		ArrayList<String[]> list		= new ArrayList<String[]>();
	    try{
	    	 
	      Statement stmt=(Statement)this.coon.createStatement();
          // 数据的结果集合;
          ResultSet rs=(ResultSet)stmt.executeQuery(sql);
          System.out.println("--------------------------------");
          
          ResultSetMetaData rsmd 		= rs.getMetaData() ; 
          int 				columnCount = rsmd.getColumnCount();
          
//          System.out.println("数据的列数:"+columnCount);
         
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
	    
	    long 				time2		= System.currentTimeMillis();
        long				timeDist	= time2-time1;
        System.out.println("数据库操作耗费:"+timeDist+"毫秒!");
        System.out.println("--------------------------------");
        
        return list;
   }
}
