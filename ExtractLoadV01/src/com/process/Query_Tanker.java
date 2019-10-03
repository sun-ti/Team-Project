package com.process;

import java.util.ArrayList;

import com.model.Util_DBase;
import com.model.Util_Net;
import com.models.Utils_DBase;

import net.sf.json.JSONArray;

public class Query_Tanker extends Util_DBase implements Utils_DBase{

	//	网络的操作控件;
	private Util_Net util_Net;
	public Query_Tanker(Util_Net util_Net) {
		super();
		this.util_Net=util_Net;
		//	进行数据库的连接;
		super.LinkDatabase(this.util_Net);
	}
	
	//	进行查询的操作;
	public String query() {
		String autoid  = null, uuid = null, orderNum = null, price = null, quantity = null, money = null, payType = null,
			   cardNum = null, oilGunNum = null, startTime = null, endTime = null, plateOld = null, plate = null,
			   state = "0", currentpage = null, limitcount = null, sqlall = "", sql = "";

		int first = 1, nlimitcount = 10;
		ArrayList<String> list = new ArrayList<String>();
		
		
		try {
			
			autoid   = util_Net.getRequest().getParameter("autoid");
			uuid   	 = util_Net.getRequest().getParameter("uuid");

			orderNum = util_Net.getRequest().getParameter("orderNum");
			price	 = util_Net.getRequest().getParameter("price");
			
			quantity = util_Net.getRequest().getParameter("quantity");
			money	 = util_Net.getRequest().getParameter("money");
			payType  = util_Net.getRequest().getParameter("payType");
			cardNum  = util_Net.getRequest().getParameter("cardNum");
			oilGunNum= util_Net.getRequest().getParameter("oilGunNum");
			startTime= util_Net.getRequest().getParameter("startTime");
			endTime	 = util_Net.getRequest().getParameter("endTime");
			plateOld = util_Net.getRequest().getParameter("plateOld");
			plate	 = util_Net.getRequest().getParameter("plate");
			state	 = util_Net.getRequest().getParameter("state");
			
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
	
		if(orderNum!=null&&!orderNum.trim().equals("")) {
			list.add("orderNum='"+orderNum+"'");
		}
		
		if(price!=null&&!price.trim().equals("")) {
			list.add("price="+price);
		}
	
		//	
		if(quantity!=null&&!quantity.trim().equals("")) {
			list.add("quantity="+quantity);
		}
	
		if(money!=null&&!money.trim().equals("")) {
			list.add("money="+money);
		}
		
		if(payType!=null&&!payType.trim().equals("")) {
			list.add("payType="+payType);
		}
		
		if(cardNum!=null&&!cardNum.trim().equals("")) {
			list.add("cardNum='"+cardNum+"'");
		}
		
		if(oilGunNum!=null&&!oilGunNum.trim().equals("")) {
			list.add("oilGunNum='"+oilGunNum+"'");
		}
		
		if(startTime!=null&&!startTime.trim().equals("")) {
			list.add("startTime='"+startTime+"'");
		}
		
		if(endTime!=null&&!endTime.trim().equals("")) {
			list.add("endTime='"+endTime+"'");
		}
		
		if(plateOld!=null&&!plateOld.trim().equals("")) {
			list.add("plateOld='"+plateOld+"'");
		}
		
		if(plate!=null&&!plate.trim().equals("")) {
			list.add("plate='"+plate+"'");
		}
		//	进行状态的查询;
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
		sql   = "select * from jyj "+where+" order by autoid desc limit "+first+","+nlimitcount;
		sqlall= "select count(autoid) from jyj "+where+" order by autoid";

		//	数据库查询操作;
		JSONArray 	 array = select(sql);
		//	进行整数的结果;
		int 		 count = getQueryCount(sqlall);
		
		return util_Net.sendResult("0", "OK", count, array.toString());
	}
	//	进行修改的操作;
	public String updateItem() {
		
		String uuid=null,orderNum=null,price=null,quantity=null,money=null,payType=null,cardNum=null,oilGunNum=null,endTime=null,plateOld=null,plate=null,sql="";
		
		try {
					
			uuid   	 = util_Net.getRequest().getParameter("uuid");

			orderNum = util_Net.getRequest().getParameter("orderNum");
			price	 = util_Net.getRequest().getParameter("price");
			
			quantity = util_Net.getRequest().getParameter("quantity");
			money	 = util_Net.getRequest().getParameter("money");
			payType  = util_Net.getRequest().getParameter("payType");
			cardNum  = util_Net.getRequest().getParameter("cardNum");
			oilGunNum= util_Net.getRequest().getParameter("oilGunNum");
			
			endTime	 = getCurrentDatetime(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss");
			plateOld = util_Net.getRequest().getParameter("plateOld");
			plate	 = util_Net.getRequest().getParameter("plate");
			
		} catch (Exception e) {

		}
		
		//	参数修改;
		ArrayList<String> list=new ArrayList<String>();
		if(orderNum!=null&&!orderNum.trim().equals("")) {
			list.add("orderNum='"+orderNum+"'");
		}
		
		if(price!=null&&!price.trim().equals("")) {
			list.add("price="+price);
		}

		if(quantity!=null&&!quantity.trim().equals("")) {
			list.add("quantity="+quantity);
		}
		
		if(money!=null&&!money.trim().equals("")) {
			list.add("money="+money);
		}
		if(payType!=null&&!payType.trim().equals("")) {
			list.add("payType="+payType);
		}
		if(cardNum!=null&&!cardNum.trim().equals("")) {
			list.add("cardNum='"+cardNum+"'");
		}
		if(oilGunNum!=null&&!oilGunNum.trim().equals("")) {
			list.add("oilGunNum='"+oilGunNum+"'");
		}
		
		if(endTime!=null&&!endTime.trim().equals("")) {
			list.add("endTime='"+endTime+"'");
		}
		
		if(plateOld!=null&&!plateOld.trim().equals("")) {
			list.add("plateOld='"+plateOld+"'");
		}
		
		if(plate!=null&&!plate.trim().equals("")) {
			list.add("plate='"+plate+"'");
		}
		
		String set="";
		if(list.size()>0) {
			for(String item:list) {
				set+=" "+item+",";
			}
			
			set = "set"+set.substring(0, set.length()-",".length());
			
			sql	= "update jyj "+set+" where uuid='"+uuid+"'";
		
		}		
		
		return util_Net.sendResult("0", "OK", update(sql), "null");
	}
	
	//	进行新增的操作;
	public String add() {
		
		String uuid = getUUID(), orderNum = null, price = null, quantity = null, money = null, payType = null,
				cardNum = null, oilGunNum = null, startTime = getCurrentDatetime(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss"), endTime = null, plateOld = null, plate = null,
				state = "0",sql="";

		try {
			
			orderNum= util_Net.getRequest().getParameter("orderNum");
			price= util_Net.getRequest().getParameter("price");
			quantity= util_Net.getRequest().getParameter("quantity");
			money= util_Net.getRequest().getParameter("money");
			payType= util_Net.getRequest().getParameter("payType");
			cardNum= util_Net.getRequest().getParameter("cardNum");
			oilGunNum= util_Net.getRequest().getParameter("oilGunNum");
			endTime= startTime;
			plateOld= util_Net.getRequest().getParameter("plateOld");
			plate= util_Net.getRequest().getParameter("plate");
			state= "0";

		} catch (Exception e) {

		}
		sql	=	"insert into station (uuid,orderNum,price,quantity,money,payType,cardNum,oilGunNum,startTime,endTime,plateOld,plate,state) "
				+ "values('"+uuid+"','"+orderNum+"',"+price+","+quantity+","+money+","+payType+",'"+cardNum+"','"+oilGunNum+"','"+startTime+"','"+endTime+"','"+plateOld+"','"+plate+"',"+state+")";
		
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
		sql			= "update jyj set state=1 where uuid='"+uuid+"'";
		
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
