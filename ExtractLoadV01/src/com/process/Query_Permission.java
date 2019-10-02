package com.process;

import java.util.ArrayList;

import com.model.Util_DBase;
import com.model.Util_Net;
import com.models.Utils_DBase;

import net.sf.json.JSONArray;

public class Query_Permission extends Util_DBase implements Utils_DBase{

	//	网络的操作控件;
	private Util_Net util_Net;
	public Query_Permission(Util_Net util_Net) {
		super();
		this.util_Net=util_Net;
		//	进行数据库的连接;
		super.LinkDatabase(this.util_Net);
	}
	
	//	进行查询的操作;
	public String query() {
		String autoid=null,name=null,url=null,createtime=null,uuid=null,type=null,parentuuid=null,del=null,
				currentpage = null, limitcount = null, sqlall = "", sql = "";
		
		int first = 1, nlimitcount = 10;
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			
			autoid   = util_Net.getRequest().getParameter("autoid");
			name 	 = util_Net.getRequest().getParameter("name");
			url 	 = util_Net.getRequest().getParameter("url");
			
			createtime = util_Net.getRequest().getParameter("createtime");
			uuid	   = util_Net.getRequest().getParameter("uuid");
			type	   = util_Net.getRequest().getParameter("type");
			parentuuid = util_Net.getRequest().getParameter("parentuuid");
			del		   = util_Net.getRequest().getParameter("del");
			
			
			currentpage= util_Net.getRequest().getParameter("page");
			limitcount = util_Net.getRequest().getParameter("limit");
		} catch (Exception e) {

		}
	
		//	判断字段;

		if(autoid!=null&&!autoid.trim().equals("")) {
			list.add("PERMISSION_AutoID="+autoid);
		}
		

		if(name!=null&&!name.trim().equals("")) {
			list.add("PERMISSION_NAME='"+name+"'");
		}
	
		if(url!=null&&!url.trim().equals("")) {
			list.add("PERMISSION_URL='"+url+"'");
		}
		
	
		if(createtime!=null&&!createtime.trim().equals("")) {
			list.add("PERMISSION_CREATE_TIME='"+createtime+"'");
		}
	
		if(uuid!=null&&!uuid.trim().equals("")) {
			list.add("PERMISSION_UUID='"+uuid+"'");
		}
		
		if(type!=null&&!type.trim().equals("")) {
			list.add("PERMISSION_TYPE="+type);
		}
		
		if(parentuuid!=null&&!parentuuid.trim().equals("")) {
			list.add("PERMISSION_PARENT_UUID='"+parentuuid+"'");
		}
		
		if(del!=null&&!del.trim().equals("")) {
			list.add("PERMISSION_DEL="+del);
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
		
		//	进行相应的查询内容;
		sql   = "select * from permission "+where+" order by PERMISSION_AutoID desc limit "+first+","+nlimitcount;
		sqlall= "select count(PERMISSION_AutoID) from permission "+where+" order by PERMISSION_AutoID desc";

		//	数据库查询操作;
		JSONArray 	 array = select(sql);

		int 		 count = getQueryCount(sqlall);
		
		return util_Net.sendResult("0", "OK", count, array.toString());
	}
	//	进行修改的操作;
	public String updateItem() {
		String name=null,url=null,uuid=null,type=null,parentuuid=null,del=null,sql="";
		
		try {
		
			name 	 = util_Net.getRequest().getParameter("name");
			url 	 = util_Net.getRequest().getParameter("url");
		
			uuid	   = util_Net.getRequest().getParameter("uuid");
			type	   = util_Net.getRequest().getParameter("type");
			parentuuid = util_Net.getRequest().getParameter("parentuuid");
			del		   = util_Net.getRequest().getParameter("del");
		
		} catch (Exception e) {

		}
		
		//	参数修改;
		ArrayList<String> list=new ArrayList<String>();
		if(name!=null&&!name.trim().equals("")) {
			list.add("PERMISSION_NAME='"+name+"'");
		}
		
		if(url!=null&&!url.trim().equals("")) {
			list.add("PERMISSION_URL='"+url+"'");
		}
		
		if(type!=null&&!type.trim().equals("")) {
			list.add("PERMISSION_TYPE="+type);
		}
		
		if(parentuuid!=null&&!parentuuid.trim().equals("")) {
			list.add("PERMISSION_PARENT_UUID='"+parentuuid+"'");
		}
		
		if(del!=null&&!del.trim().equals("")) {
			list.add("PERMISSION_DEL="+del);
		}
		
		
		String set="";
		if(list.size()>0) {
			for(String item:list) {
				set+=" "+item+",";
			}
			
			set="set"+set.substring(0, set.length()-",".length());
			
			sql	= "update permission "+set+" where PERMISSION_UUID='"+uuid+"'";
		
		}		
		
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}
	
	//	进行新增的操作;
	public String add() {
		
		String name	=null,url=null,type=null,parentuuid=null,sql="";
		String uuid	= getUUID();
		String time = getCurrentDatetime(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss");
		try {
			
			
			name 	 = util_Net.getRequest().getParameter("name");
			url 	 = util_Net.getRequest().getParameter("url");
			
			type	   = util_Net.getRequest().getParameter("type");
			parentuuid = util_Net.getRequest().getParameter("parentuuid");
			
			
		} catch (Exception e) {

		}
				
		sql="insert into permission (PERMISSION_AutoID,PERMISSION_NAME,PERMISSION_URL,PERMISSION_CREATE_TIME,PERMISSION_UUID,PERMISSION_TYPE,PERMISSION_PARENT_UUID,PERMISSION_DEL) values('"+name+"','"+url+"','"+time+"','"+uuid+"',"+type+",'"+parentuuid+"',0)";
		
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}
	
	//	进行删除的操作;
	public String del() {
		String sql	= "";
		String uuid = null;
		
		try {
			uuid    = util_Net.getRequest().getParameter("uuid");
		} catch (Exception e) {
			// TODO: handle exception
		}
		sql			= "update permission set PERMISSION_DEL=1 where PERMISSION_UUID='"+uuid+"'";
		
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
