package com.process;

import java.util.ArrayList;

import com.model.Util;
import com.model.Util_DBase;
import com.model.Util_Net;
import com.models.Utils_DBase;

import net.sf.json.JSONArray;

public class Query_User extends Util_DBase implements Utils_DBase{

	private Util_Net util_Net;
	
	public Query_User(Util_Net util_Net) {
		super();
		this.util_Net=util_Net;
		//	进行数据库的连接;
		super.LinkDatabase(this.util_Net);
	}
	

    //	4.获得相应的数据的总数;
	public String queryCount() {
		String 	  sql	 	= "select count(*) from user where USER_DEL=0";
		return util_Net.sendResult("0", "OK", getQueryCount(sql), getQueryCount(sql)+"");
	}
	
	//	5.根据用户查询角色;
	public String queryRoleAccordingUser() {
		String[]  results= {"1","NO"};
		String 	  result = null;
		int 	  size	 = 0;
		
		String 	  userid = util_Net.getRequest().getParameter("userUuid");
		
		String 	  sql	 = "select b.ROLE_UUID,b.ROLE_NAME from user_role a, role b where a.USER_UUID='"+userid+"' and a.ROLE_UUID=b.ROLE_UUID";
		
		JSONArray array	 = super.select(sql);
		
		if(array!=null) {
			results[0]= "0";
			results[1]= "OK";
			result	  = array.toString();
			size	  = array.size();
		}

		return util_Net.sendResult(results[0], results[1], size, result);
	}
	
	//	6.根据用户增加角色;
	public String addRoleAccordingUser() {
		
		
		String 	  userid = util_Net.getRequest().getParameter("userUuid");
		String 	  roleid = util_Net.getRequest().getParameter("roleUuid");
		String 	  ur_id	 = getUUID();
		String 	  datetime=getCurrentDatetime(System.currentTimeMillis(), Util.TAG_DATETIME);
		
		String 	  sql	 	= "insert into user_role(USER_UUID,ROLE_UUID,UR_UUID,UR_CREATE_TIME,UR_DEL) values ('"+userid+"','"+roleid+"','"+ur_id+"','"+datetime+"',0)";
		System.out.println(sql);
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}
	//	7.根据用户删除角色;
	public String delRoleAccordingUser() {
		
		String 	  userid = util_Net.getRequest().getParameter("userUuid");
		String 	  roleid = util_Net.getRequest().getParameter("roleUuid");
		
		String 	  sql	 = "update user_role set UR_DEL=1 where USER_UUID='"+userid+"' and ROLE_UUID='"+roleid+"'";
		int 	  count	 = 0;
		count			 = super.update(sql);
		return util_Net.sendResult("0", "OK", count, "null");
	}
	//	8.根据用户查询权限;
	public String queryPermissionAccordingUser() {
		
		String[]  results= {"1","NO"};
		String 	  result = null;
		int 	  size	 = 0;
		
		String 	  userid = util_Net.getRequest().getParameter("userUuid");
		
		String 	  sql	 = 
		"select b.ROLE_UUID,b.ROLE_NAME,c.PERMISSION_UUID,c.PERMISSION_NAME from user_role a, role b, permission c,role_permission d "
		+ "where a.USER_UUID='"+userid+"' and a.ROLE_UUID=b.ROLE_UUID and c.PERMISSION_UUID=d.PERMISSION_UUID";
		
		JSONArray array	 = super.select(sql);
		
		if(array!=null) {
			results[0]= "0";
			results[1]= "OK";
			result	  = array.toString();
			size	  = array.size();
		}

		return util_Net.sendResult(results[0], results[1], size, result);
	}
	
	//	进行查询的操作;
	public String query() {
				
		String autoid = null, username = null, password = null, createtime = null, refreshtime = null,
				refreshmark = null, del = null, uuid = null,currentpage = null, limitcount = null, sqlall = "", sql = "";
			
		int first = 1, nlimitcount = 10;
		ArrayList<String> list = new ArrayList<String>();

		try {
			autoid   = util_Net.getRequest().getParameter("autoid");
			username = util_Net.getRequest().getParameter("username");
			password = util_Net.getRequest().getParameter("password");
			
			createtime = util_Net.getRequest().getParameter("createtime");
			refreshtime= util_Net.getRequest().getParameter("refreshtime");
			refreshmark= util_Net.getRequest().getParameter("refreshmark");
			del		   = util_Net.getRequest().getParameter("del");
			uuid	   = util_Net.getRequest().getParameter("uuid");
			
			currentpage= util_Net.getRequest().getParameter("page");
			limitcount = util_Net.getRequest().getParameter("limit");
		} catch (Exception e) {
			// TODO: handle exception
		}

		//	判断字段;

		if(autoid!=null&&!autoid.trim().equals("")) {
			list.add("USER_AutoID="+autoid);
		}
		

		if(username!=null&&!username.trim().equals("")) {
			list.add("USERNAME='"+username+"'");
		}
	
		if(password!=null&&!password.trim().equals("")) {
			list.add("PASSWORD='"+password+"'");
		}
		
	
		if(createtime!=null&&!createtime.trim().equals("")) {
			list.add("USER_CREATE_TIME='"+createtime+"'");
		}
	
		if(refreshtime!=null&&!refreshtime.trim().equals("")) {
			list.add("USER_REFRESH_TIME='"+refreshtime+"'");
		}
		
		if(refreshmark!=null&&!refreshmark.trim().equals("")) {
			list.add("USER_REFRESH_MARK="+refreshmark);
		}
		
		if(del!=null&&!del.trim().equals("")) {
			list.add("USER_DEL="+del);
		}
		
		if(uuid!=null&&!uuid.trim().equals("")) {
			list.add("USER_UUID='"+uuid+"'");
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
			where="where"+where.subSequence(0, where.length()-"and".length())+" and USER_DEL=0";	
		}else
			where="where USER_DEL=0";
		
		//	进行相应的查询内容;
		sql   = "select * from user "+where+" order by USER_AutoID desc limit "+first+","+nlimitcount;

		sqlall= "select count(USER_AutoID) from user "+where+" order by USER_AutoID desc";
	
		//	数据库查询操作;
		JSONArray 	 array = select(sql);

		int 		 count = getQueryCount(sqlall);
		
		return util_Net.sendResult("0", "OK", count, array.toString());
	}
	//	进行修改的操作;
	public String updateItem() {

		String  username = null, password = null, uuid = null, sql = "";
		int 	count	 = 0;
		try {

			username = util_Net.getRequest().getParameter("username");
			password = util_Net.getRequest().getParameter("password");
		
			uuid	   = util_Net.getRequest().getParameter("uuid");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		ArrayList<String> list=new ArrayList<String>();
		if(username!=null&&!username.trim().equals("")) {
			list.add("USERNAME='"+username+"'");
		}
		
		if(password!=null&&!password.trim().equals("")) {
			list.add("PASSWORD='"+password+"'");
		}
		String set="";
		if(list.size()>0) {
			for(String item:list) {
				set+=" "+item+",";
			}
			
			set="set"+set+" USER_REFRESH_TIME='"+getCurrentDatetime(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss")+"',USER_REFRESH_MARK=1";
			
			sql	= "update user "+set+" where USER_UUID='"+uuid+"'";
		
			count=update(sql);
		}		
		
		return util_Net.sendResult("0", "OK",count, "null");
	}
	
	//	进行新增的操作;
	public String add() {
		String username = null, password = null,sql="";
		String uuid	    = getUUID();
		String time		= getCurrentDatetime(System.currentTimeMillis(),Util.TAG_DATETIME);
		
		try {
			username = util_Net.getRequest().getParameter("username");
			password = util_Net.getRequest().getParameter("password");
						
		} catch (Exception e) {
			// TODO: handle exception
		}
				
		sql="insert into user (USERNAME,PASSWORD,USER_CREATE_TIME,USER_REFRESH_TIME,USER_REFRESH_MARK,USER_DEL,USER_UUID) values('"+username+"','"+password+"','"+time+"','"+time+"',0,0,'"+uuid+"')";

		return util_Net.sendResult("0", "OK", update(sql), "null");
	}
	
	//	进行删除的操作;
	public String del() {
		String uuid	= null,sql="";
		try {
			uuid	= util_Net.getRequest().getParameter("uuid");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		sql			= "update user set USER_DEL=1 where USER_UUID='"+uuid+"'";
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}
	
	
	@Override
	public JSONArray select(String sql) {
		
		return super.select(sql);
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
