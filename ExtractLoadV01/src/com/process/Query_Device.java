package com.process;

import java.util.ArrayList;

import com.model.Util_DBase;
import com.model.Util_Net;
import com.models.Utils_DBase;

import net.sf.json.JSONArray;

public class Query_Device extends Util_DBase implements Utils_DBase{

	//	网络的操作控件;
	private Util_Net util_Net;
	
	public Query_Device(Util_Net util_Net) {
		super();
		this.util_Net=util_Net;
		//	进行数据库的连接;
		super.LinkDatabase(this.util_Net);
	}
	
	//	进行查询的操作;
	public String query() {

		String autoid=null,uuid=null,deviceip=null,stationid=null,kind=null,note=null,state=null,
				currentpage = null, limitcount = null, sqlall = "", sql = "";
		
		int first = 1, nlimitcount = 10;
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			
			autoid   = util_Net.getRequest().getParameter("autoid");
			uuid   	 = util_Net.getRequest().getParameter("uuid");
			deviceip = util_Net.getRequest().getParameter("deviceip");
			stationid= util_Net.getRequest().getParameter("stationid");
			kind	 = util_Net.getRequest().getParameter("kind");
			note 	 = util_Net.getRequest().getParameter("note");
			state 	 = util_Net.getRequest().getParameter("state");
			
			
			currentpage= util_Net.getRequest().getParameter("page");
			limitcount = util_Net.getRequest().getParameter("limit");
		} catch (Exception e) {

		}
	
		//	判断字段;

		if(autoid!=null&&!autoid.trim().equals("")) {
			list.add("autoid="+autoid);
		}
		

		if(uuid!=null&&!uuid.trim().equals("")) {
			list.add("uuid='"+uuid+"'");
		}
	
		if(deviceip!=null&&!deviceip.trim().equals("")) {
			list.add("deviceip='"+deviceip+"'");
		}
		
		if(stationid!=null&&!stationid.trim().equals("")) {
			list.add("stationid='"+stationid+"'");
		}
		
		if(kind!=null&&!kind.trim().equals("")) {
			list.add("kind='"+kind+"'");
		}
	
		if(note!=null&&!note.trim().equals("")) {
			list.add("note='"+note+"'");
		}
		
		if(state!=null&&!state.trim().equals("")) {
			list.add("state="+state);
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
		sql   = "select * from device "+where+" order by autoid desc limit "+first+","+nlimitcount;
		sqlall= "select count(autoid) from device "+where;

		//	数据库查询操作;
		JSONArray 	 array = select(sql);

		int 		 count = getQueryCount(sqlall);
		
		return util_Net.sendResult("0", "OK", count, array.toString());
	}
	//	进行修改的操作;
	public String updateItem() {
		
		String uuid=null,deviceip=null,stationid=null,kind=null,note=null,sql="";
		
		try {
		
			uuid   	 = util_Net.getRequest().getParameter("uuid");
			deviceip = util_Net.getRequest().getParameter("deviceip");
			stationid= util_Net.getRequest().getParameter("stationid");
			kind	 = util_Net.getRequest().getParameter("kind");
			note 	 = util_Net.getRequest().getParameter("note");
		
		} catch (Exception e) {

		}
		
		//	参数修改;
		ArrayList<String> list=new ArrayList<String>();
		if(deviceip!=null&&!deviceip.trim().equals("")) {
			list.add("deviceip='"+deviceip+"'");
		}
		
		if(stationid!=null&&!stationid.trim().equals("")) {
			list.add("stationid='"+stationid+"'");
		}

		if(kind!=null&&!kind.trim().equals("")) {
			list.add("kind="+kind);
		}
		
		if(note!=null&&!note.trim().equals("")) {
			list.add("note='"+note+"'");
		}
		String set="";
		if(list.size()>0) {
			for(String item:list) {
				set+=" "+item+",";
			}
			
			set = "set"+set.substring(0, set.length()-",".length());
			
			sql	= "update device "+set+" where uuid='"+uuid+"'";
		
		}		
		
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}
	
	//	进行新增的操作;
	public String add() {

		String uuid=getUUID(),deviceip=null,stationid=null,kind=null,note=null,sql="";

		try {
			deviceip= util_Net.getRequest().getParameter("deviceip");
			stationid= util_Net.getRequest().getParameter("stationid");
			kind= util_Net.getRequest().getParameter("kind");
			note= util_Net.getRequest().getParameter("note");

		} catch (Exception e) {

		}
		sql	=	"insert into device (uuid,deviceip,stationid,kind,note,state) values('"+uuid+"','"+deviceip+"','"+stationid+"',"+kind+",'"+note+"',0)";

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
		sql			= "update device set state=1 where uuid='"+uuid+"'";
		
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
