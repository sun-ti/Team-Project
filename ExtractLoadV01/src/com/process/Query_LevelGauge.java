package com.process;

import java.util.ArrayList;

import com.model.Util_DBase;
import com.model.Util_Net;
import com.models.Utils_DBase;

import net.sf.json.JSONArray;

public class Query_LevelGauge extends Util_DBase implements Utils_DBase{

	//	网络的操作控件;
	private Util_Net util_Net;
	public Query_LevelGauge(Util_Net util_Net) {
		super();
		this.util_Net=util_Net;
		//	进行数据库的连接;
		super.LinkDatabase(this.util_Net);
	}
	
	//	进行查询的操作;
	public String query() {
		String autoid=null,uuid=null,nodeno=null,xql=null,ps_date=null,cp_no=null,depot_code=null,psd_id=null,oils_id=null,
				currentpage = null, limitcount = null, sqlall = "", sql = "";
		
		int first = 1, nlimitcount = 10;
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			
			autoid    = util_Net.getRequest().getParameter("autoid");
			uuid   	  = util_Net.getRequest().getParameter("uuid");
			
			nodeno	  = util_Net.getRequest().getParameter("nodeno");
			xql 	  = util_Net.getRequest().getParameter("xql");
			
			ps_date	  = util_Net.getRequest().getParameter("ps_date");
			cp_no	  = util_Net.getRequest().getParameter("cp_no");
			depot_code= util_Net.getRequest().getParameter("depot_code");
			psd_id	  = util_Net.getRequest().getParameter("psd_id");
			oils_id	  = util_Net.getRequest().getParameter("oils_id");

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
	
		if(nodeno!=null&&!nodeno.trim().equals("")) {
			list.add("nodeno='"+nodeno+"'");
		}
		
		if(xql!=null&&!xql.trim().equals("")) {
			list.add("xql='"+xql+"'");
		}
	
		if(ps_date!=null&&!ps_date.trim().equals("")) {
			list.add("ps_date='"+ps_date+" 00:00:00'");
		}
		
		if(cp_no!=null&&!cp_no.trim().equals("")) {
			list.add("cp_no='"+cp_no+"'");
		}
		
		if(depot_code!=null&&!depot_code.trim().equals("")) {
			list.add("depot_code='"+depot_code+"'");
		}
		
		if(psd_id!=null&&!psd_id.trim().equals("")) {
			list.add("psd_id='"+psd_id+"'");
		}
		
		if(oils_id!=null&&!oils_id.trim().equals("")) {
			list.add("oils_id='"+oils_id+"'");
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
		sql   = "select * from ywy "+where+" order by autoid desc limit "+first+","+nlimitcount;
		sqlall= "select count(autoid) from ywy "+where+" order by autoid";

		//	数据库查询操作;
		JSONArray 	 array = select(sql);

		int 		 count = getQueryCount(sqlall);
		
		return util_Net.sendResult("0", "OK", count, array.toString());
	}
	//	进行修改的操作;
	public String updateItem() {
		
		String uuid=null,nodeno=null,xql=null,ps_date=null,cp_no=null,depot_code=null,psd_id=null,oils_id=null,sql="";
		
		try {
		
			uuid 	   = util_Net.getRequest().getParameter("uuid");
			
			//	进行修改;
			nodeno	  = util_Net.getRequest().getParameter("nodeno");
			xql 	  = util_Net.getRequest().getParameter("xql");
			
			ps_date	  = util_Net.getRequest().getParameter("ps_date");
			cp_no	  = util_Net.getRequest().getParameter("cp_no");
			depot_code= util_Net.getRequest().getParameter("depot_code");
			psd_id	  = util_Net.getRequest().getParameter("psd_id");
			oils_id	  = util_Net.getRequest().getParameter("oils_id");
					
		} catch (Exception e) {

		}
		
		//	参数修改;
		ArrayList<String> list=new ArrayList<String>();
		
		//	进行修改参数的内容;
		if(nodeno!=null&&!nodeno.trim().equals("")) {
			list.add("nodeno='"+nodeno+"'");
		}
		
		if(xql!=null&&!xql.trim().equals("")) {
			list.add("xql='"+xql+"'");
		}

		if(ps_date!=null&&!ps_date.trim().equals("")) {
			list.add("ps_date='"+ps_date+"'");
		}
		
		if(cp_no!=null&&!cp_no.trim().equals("")) {
			list.add("cp_no='"+cp_no+"'");
		}
		
		if(depot_code!=null&&!depot_code.trim().equals("")) {
			list.add("depot_code='"+depot_code+"'");
		}
		
		if(psd_id!=null&&!psd_id.trim().equals("")) {
			list.add("psd_id='"+psd_id+"'");
		}
		
		if(oils_id!=null&&!oils_id.trim().equals("")) {
			list.add("oils_id='"+oils_id+"'");
		}
		//	设置相应的参数信息;
		String set="";
		if(list.size()>0) {
			for(String item:list) {
				set+=" "+item+",";
			}
			
			set = "set"+set.substring(0, set.length()-",".length());
			
			sql	= "update ywy "+set+" where uuid='"+uuid+"'";
		
		}		
		
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}
	
	//	进行新增的操作;
	public String add() {
		
		String uuid	=getUUID(),nodeno=null,xql=null,ps_date=null,cp_no=null,depot_code=null,psd_id=null,oils_id=null,state="0",sql="";
				
		try {
			
			//	进行修改;
			nodeno	  = util_Net.getRequest().getParameter("nodeno");
			xql 	  = util_Net.getRequest().getParameter("xql");
			
			ps_date	  = util_Net.getRequest().getParameter("ps_date");
			cp_no	  = util_Net.getRequest().getParameter("cp_no");
			depot_code= util_Net.getRequest().getParameter("depot_code");
			psd_id	  = util_Net.getRequest().getParameter("psd_id");
			oils_id	  = util_Net.getRequest().getParameter("oils_id");
			
		} catch (Exception e) {

		}
		sql	=	"insert into ywy (uuid,nodeno,xql,ps_date,cp_no,depot_code,psd_id,oils_id,state) values"
				+ "('"+uuid+"','"+nodeno+"','"+xql+"','"+ps_date+"','"+cp_no+"','"+depot_code+"','"+psd_id+"','"+oils_id+"',"+state+")";
		
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}
	
	//	进行删除的操作;
	public String del() {
		String sql	= "";
		String uuid = null;
		//	进行数据的修改;
		try {
			uuid    = util_Net.getRequest().getParameter("uuid");
		} catch (Exception e) {
			// TODO: handle exception
		}
		sql			= "update ywy set state=1 where uuid='"+uuid+"'";
		
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
