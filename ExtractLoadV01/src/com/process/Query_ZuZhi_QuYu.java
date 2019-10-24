package com.process;

import java.util.ArrayList;

import com.model.Util;
import com.model.Util_DBase;
import com.model.Util_Net;
import com.models.Utils_DBase;

import net.sf.json.JSONArray;

public class Query_ZuZhi_QuYu extends Util_DBase implements Utils_DBase{

	//	网络的操作控件;
	private Util_Net util_Net;
	public Query_ZuZhi_QuYu(Util_Net util_Net) {
		super();
		this.util_Net=util_Net;
		//	进行数据库的连接;
		super.LinkDatabase(this.util_Net);
	}
	
	//	根据公司删除区域;
	public String delStationByQuYu() {
		String quyu_id	= null,stationid=null,sql="";
		String code			= "1",	errDesc="NO", result=null;
		int    count		= 0;
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			quyu_id		= util_Net.getRequest().getParameter("quyu_id");
			stationid	= util_Net.getRequest().getParameter("stationid");
			
		} catch (Exception e) {

		}
		
		if(quyu_id!=null&&!quyu_id.trim().equals("")) {
			list.add("quyu_id='"+quyu_id+"'");
		}
		
		if(stationid!=null&&!stationid.trim().equals("")) {
			list.add("stationid='"+stationid+"'");
		}
		
		String where="";
		
		if(list.size()!=0) {
			for(String item:list) {
				where+=" "+item+" and";
			}
			where="where"+where.subSequence(0, where.length()-"and".length());	
		}
		
		sql		=	"update quyu_zhandian set state=1 "+where;
//		System.out.println(sql);
		//	进行数据的更新;
		count	=	update(sql);
		
		if(count!=0) {
			code	= "0";
			errDesc = "OK";
		}
		
		return util_Net.sendResult(code,errDesc, count,result);
	}
	
	//	根据区域添加站点;
	public String addStationByQuYu() {
		String quyu_id	= null,stationid=null,sql="";
		String code			= "1",	errDesc="NO", result=null;
		int    count		= 0;
		
		try {
			quyu_id		= util_Net.getRequest().getParameter("quyu_id");
			stationid	= util_Net.getRequest().getParameter("stationid");
			
		} catch (Exception e) {

		}
		
		sql		=	"insert into quyu_zhandian (quyu_id,stationid,state) values ('"+quyu_id+"','"+stationid+"',0)";
		//	进行数据的更新;
		count	=	update(sql);
		
		if(count!=0) {
			code	= "0";
			errDesc = "OK";
		}
		
		return util_Net.sendResult(code,errDesc, count,result);
	}
	
	//	根据区域查询站点;
	public String queryStationByQuYu() {
		
		String quyu_id	= null;
		String code			= "1",	errDesc="NO", result=null;
		String currentpage 	= null, limitcount = null, sqlall = "", sql = "";
		int    first 		= 1, nlimitcount = 10;
		
		int    count		= 0;
		
		try {
			quyu_id	= util_Net.getRequest().getParameter("quyu_id");
			
			//	进行相应的数据的处理;
			currentpage = util_Net.getRequest().getParameter("page");
			limitcount  = util_Net.getRequest().getParameter("limit");
		} catch (Exception e) {
			// TODO: handle exception
		}
		//	currentpage判断是否存在;
		if(limitcount!=null&&!limitcount.trim().equals("")) {
			nlimitcount=Integer.parseInt(limitcount);
		}

		if(currentpage!=null&&!currentpage.trim().equals("")) {
			first	    = (Integer.parseInt(currentpage)-1)*nlimitcount;
		}
		
		if(quyu_id!=null&&!quyu_id.equals("")) {
			
			sql			= "select b.stationid,b.name from zuzhi_quyu a,station b,quyu_zhandian c where a.quyu_id=c.quyu_id and b.stationid=c.stationid and c.quyu_id and c.state=0 and c.quyu_id='"+quyu_id+"' limit "+first+","+nlimitcount;	
			sqlall		= "select count(c.autoid) from zuzhi_quyu a,station b,quyu_zhandian c where a.quyu_id=c.quyu_id and b.stationid=c.stationid and c.quyu_id and c.state=0 and c.quyu_id='"+quyu_id+"'";
			
			//	数据库查询操作;
			JSONArray 	 array = select(sql);
			
			if(array!=null&&array.size()!=0) {
				count  = getQueryCount(sqlall);
				code   = "0";
				errDesc= "OK";
				result = array.toString();	
			}
		}
		
		return util_Net.sendResult(code,errDesc, count,result);
	}
	
	
	
	//	进行查询的操作;
	public String query() {
		
		String quyu_id		=	null,	quyu_name=null,note=null,state="0";
		
		String currentpage 	= 	null, limitcount = null, sqlall = "", sql = "";
		String code			= "1",	errDesc="NO", result=null;	
		
		int    first 		= 	1, nlimitcount = 10;
		int    count		=	0;
		
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			
			quyu_id   = util_Net.getRequest().getParameter("quyu_id");
			quyu_name = util_Net.getRequest().getParameter("quyu_name");
			note	  = util_Net.getRequest().getParameter("note");
			
			state 	 = util_Net.getRequest().getParameter("state");
			
			currentpage= util_Net.getRequest().getParameter("page");
			limitcount = util_Net.getRequest().getParameter("limit");
			
		} catch (Exception e) {

		}
	
		//	判断字段;
		if(quyu_id!=null&&!quyu_id.trim().equals("")) {
			list.add("quyu_id='"+quyu_id+"'");
		}
		

		if(quyu_name!=null&&!quyu_name.trim().equals("")) {
			list.add("quyu_name='"+quyu_name+"'");
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
			where="where"+where.subSequence(0, where.length()-"and".length())+" and state=0";	
		}else
			where="where state=0";
			
		//	进行相应的查询内容;
		sql   		= "select * from zuzhi_quyu "+where+" order by datetime1 desc limit "+first+","+nlimitcount;
		sqlall		= "select count(autoid) from zuzhi_quyu "+where+" order by datetime1 desc";
		System.out.println(sql);
		//	数据库查询操作;
		JSONArray 	 array = select(sql);

		count 		= getQueryCount(sqlall);

		if(count!=0) {
			code		= "0";
			errDesc		= "OK";
			result		= array.toString();	
		}
		
		
		return util_Net.sendResult(code, errDesc, count, result);
	}
	//	进行修改的操作;
	public String updateItem() {
		
		String quyu_id	=  null,quyu_name=null,note=null,sql="";
		
		int    count	=  0;
		String code		=  "1",result="NO";
		
		try {
		
			quyu_id 	=  util_Net.getRequest().getParameter("quyu_id");
			quyu_name   =  util_Net.getRequest().getParameter("quyu_name");
			note	   	=  util_Net.getRequest().getParameter("note");
			
		} catch (Exception e) {

		}
		
		//	参数修改;
		ArrayList<String> list=new ArrayList<String>();
		
		if(quyu_id!=null&&!quyu_id.equals("")) {
		
			if(quyu_name!=null&&!quyu_name.trim().equals("")) {
				list.add("quyu_name='"+quyu_name+"'");
			}
			
			if(note!=null&&!note.trim().equals("")) {
				list.add("note='"+note+"'");
			}
			
			String set="";
			
			if(list.size()>0) {
				for(String item:list) {
					set+=" "+item+",";
				}
				
				set   = "set"+set.substring(0, set.length()-",".length());
				
				sql	  = "update zuzhi_quyu "+set+" where quyu_id='"+quyu_id+"'";
			System.out.println(sql);
				count = update(sql);
				code  = "0";
				result= "OK";
			}	
			
		}
		
		return util_Net.sendResult(code, result, count, "null");
	}
	
	//	进行新增的操作;
	public String add() {	
		
		String quyu_id	=  null,quyu_name=null,note=null,sql="";
		long   datetime1=  System.currentTimeMillis();	
		String datetime =  getCurrentDatetime(datetime1,Util.TAG_DATETIME);	
		String state	=  "0";
		int    count	=  0;
		String code		=  "1",result="NO";
		
		try {
			
			quyu_id	 	=  util_Net.getRequest().getParameter("quyu_id");
			quyu_name 	=  util_Net.getRequest().getParameter("quyu_name");
			note 		=  util_Net.getRequest().getParameter("note");

			sql			=  "insert into zuzhi_quyu (quyu_id,quyu_name,note,datetime,datetime1,state) values('"+quyu_id+"','"+quyu_name+"','"+note+"','"+datetime+"',"+datetime1+","+state+")";
			System.out.println(sql);
			count		=  update(sql);
			code		=  "0";
			result		=  "OK";
		
		} catch (Exception e) {

		}
		
		return util_Net.sendResult(code, result, count, "null");
	}
	
	//	进行删除的操作;
	public String del() {
		
		String quyu_id  =  null,sql="";
		int    count	=  0;
		String code		=  "1",result="NO";
		
		try {
			quyu_id     =  util_Net.getRequest().getParameter("quyu_id");
			
			sql			=  "update zuzhi_quyu set state=1 where quyu_id='"+quyu_id+"'";
			count		=  update(sql);
			
			code		=  "0";
			result		 =	"OK";
			
		} catch (Exception e) {
			
		}

		return util_Net.sendResult(code, result, count, "null");
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
