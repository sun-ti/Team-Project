package com.process;

import java.util.ArrayList;

import com.model.Util_DBase;
import com.model.Util_Net;
import com.models.Utils_DBase;

import net.sf.json.JSONArray;

public class Query_Role extends Util_DBase implements Utils_DBase {

	// 网络的对象;
	private Util_Net util_Net;

	public Query_Role(Util_Net util_Net) {
		super();
		this.util_Net = util_Net;
		//	进行数据库的连接;
		super.LinkDatabase(this.util_Net);
	}

	// 进行查询的操作;
	public String query() {
		String autoid = null, name = null, uuid = null, del = null, createtime = null, currentpage = null,
			   limitcount = null,sqlall = "",sql = "";
		
		int first = 1, nlimitcount = 10;
		ArrayList<String> list = new ArrayList<String>();

		try {
			autoid = util_Net.getRequest().getParameter("autoid");
			name   = util_Net.getRequest().getParameter("name");
			uuid   = util_Net.getRequest().getParameter("uuid");
			del    = util_Net.getRequest().getParameter("del");
			createtime = util_Net.getRequest().getParameter("createtime");
			currentpage= util_Net.getRequest().getParameter("page");
			limitcount = util_Net.getRequest().getParameter("limit");
		} catch (Exception e) {
			// TODO: handle exception
		}

		//	判断字段;
		//		lcommand判断是否存在;
		if(autoid!=null&&!autoid.trim().equals("")) {
			list.add("ROLE_AutoID="+autoid);
		}
		
		//	stationid判断是否存在;
		if(name!=null&&!name.trim().equals("")) {
			list.add("ROLE_NAME='"+name+"'");
		}
		//	deviceip判断是否存在;		
		if(uuid!=null&&!uuid.trim().equals("")) {
			list.add("ROLE_UUID='"+uuid+"'");
		}
		//	deviceip判断是否存在;		
		if(del!=null&&!del.trim().equals("")) {
			list.add("ROLE_DEL="+del);
		}
		//	deviceip判断是否存在;		
		if(createtime!=null&&!createtime.trim().equals("")) {
			list.add("ROLE_CREATE_TIME='"+createtime+"'");
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
			where="where"+where.subSequence(0, where.length()-"and".length())+" and a.ROLE_UUID=c.ROLE_UUID and b.PERMISSION_UUID=c.PERMISSION_UUID";	
		}else {
			where="where a.ROLE_UUID=c.ROLE_UUID and b.PERMISSION_UUID=c.PERMISSION_UUID";
		}
		
		//	进行相应的查询内容;
		sql   = "select a.*,b.PERMISSION_NAME,b.PERMISSION_UUID from role a,permission b,role_permission c "+where+" order by a.ROLE_AutoID desc limit "+first+","+nlimitcount;

		sqlall= "select count(a.ROLE_AutoID) from role a,permission b,role_permission c "+where+" order by a.ROLE_AutoID desc";

		//	数据库查询操作;
		JSONArray 	 array = select(sql);

		int 		 count = getQueryCount(sqlall);
		
		return util_Net.sendResult("0", "OK", count, array.toString());
	}

	// 进行修改的操作;
	public String updateItem() {

		String sql = "";
		String name= null,uuid=null;
		
		try {
			name   = util_Net.getRequest().getParameter("name");
			uuid   = util_Net.getRequest().getParameter("uuid");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		sql	= "update role set ROLE_NAME='"+name+"' where ROLE_UUID='"+uuid+"'";
		
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}

	// 进行新增的操作;
	public String add() {

		String sql 	   = "";
		String name	   = null;
		//	uuid;
		String uuid    = getUUID();
		//	时间戳;
		String datetime= getCurrentDatetime(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss");
		
		try {
			name   = util_Net.getRequest().getParameter("name");
		} catch (Exception e) {
			// TODO: handle exception
		}
				
		sql			   = "insert into role (ROLE_AutoID,ROLE_NAME,ROLE_UUID,ROLE_DEL,ROLE_CREATE_TIME) values('"+name+"','"+uuid+"',0,'"+datetime+"')";
		
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}

	// 进行删除的操作;
	public String del() {

		String sql	= "";
		String uuid = null;
		
		try {
			uuid    = util_Net.getRequest().getParameter("uuid");
		} catch (Exception e) {
			// TODO: handle exception
		}
		sql			= "update role set ROLE_DEL=1 where ROLE_UUID='"+uuid+"'";
		
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}

	@Override
	public JSONArray select(String sql) {

		return super.select(sql);
	}

	// 获得所有的数据条数;
	@Override
	public int getQueryCount(String sql) {

		return super.getQueryCount(sql);
	}

	// 修改相应的数据内容;
	@Override
	public int update(String sql) {

		return super.update(sql);
	}

}
