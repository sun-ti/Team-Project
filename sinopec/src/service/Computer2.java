package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DBaseUtils;
import model.Util_Net;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//	提交加油信息;
public class Computer2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Computer2() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    //	获得系统获得UUID的方法;
	public String getUUID() {
		return UUID.randomUUID().toString();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//	获取的参数内容;
		String 	   parameters= null;
		//	获取的参数对象;
		JSONObject object    = null;
		String 	   clientIp	 = null;
		String 	   order 	 = null;
		
		String 	   jiayou_id = null;
		String 	   orderNum	 = null;
		String 	   price	 = null;
		String 	   quantity	 = null;
		String 	   money	 = null;
		String 	   payType	 = null;
		String 	   cardNum 	 = null;
		String 	   oilGunNum = null;
		String 	   startTime = null;
		String 	   endTime	 = null;
		String 	   plateOld	 = null;
		String     plate	 = null;
		String 	   state	 = "0";
		//	进行操作的SQL语句;
		String 	   tag		 = "errorDesc";
		String 	   sql		 = "";
		JSONArray  array	 = null;
		
		//	订单参数的对象;
		JSONObject orderObj	 = null;
		
		//	进行数据库操作;
		DBaseUtils baseUtils = new DBaseUtils();

		JSONObject back	 	 = new JSONObject();
		//	返回编码;
		String 	   errCode	 = "1";
		//	返值结果;
		String 	   result	 = null;

		//	开头设置;
		Util_Net 	util_Net = new Util_Net(request,response);
		//	网络数据的开头内容;
		util_Net.setHttpServletParameter();

		try {
			
			//	方法的解析;
			System.out.println("实现submitOrder");
			//	进行参数的相应数据信息;
			parameters		 = request.getParameter("parameters").toString().trim();
			
			if(parameters!=null&&!parameters.equals("")) {
			
				//	判断参数中是否有相同的内容变量;
				if(parameters.contains("\"{")) {
					parameters=parameters.replace("\"{", "{");
				}
				
				if(parameters.contains("}\"")) {
					parameters=parameters.replace("}\"", "}");
				}
				
				object			 = JSONObject.fromObject(parameters);
	
				//	加油机的ip地址;
				clientIp		 = object.getString("clientIp");
				//	提交的指令信息;
				order		 	 = object.getString("order");
				
				//	数据新增的操作;
				System.out.println("client:"+clientIp+" order:"+order);
				
				//	根据clientIp地址获取相应的站点信息;
				sql				 = "select a.stationid from zhandian_jiayouji a,shebei_jiayouji b where a.jiayouji_id=b.jiayouji_id and b.jiayouji_ip='"+clientIp+"'";
				array			 = baseUtils.select2(sql);
				
				//	进行数据结果的查询;
				if(array!=null) {
					
					if(array.size()!=0) {
						
						//	车站id编号;
						String stationid = array.getJSONObject(0).getString("stationid").toString().trim();
						System.out.println("clientIp:"+clientIp+" 进行数据更新!");
						
						orderObj		 = JSONObject.fromObject(order);
		
						jiayou_id		 =	System.currentTimeMillis()+"";
						//	数据插入内容;
						orderNum		 =  orderObj.getString("orderNum");
						
						price			 =  orderObj.getString("price");
						
						quantity		 =	orderObj.getString("quantity");
						money			 =	orderObj.getString("money");
						
						payType			 =	orderObj.getString("payType");
						cardNum			 =	orderObj.getString("cardNum");
						oilGunNum		 =	orderObj.getString("oilGunNum");
						startTime		 =	orderObj.getString("startTime");
						endTime			 =	orderObj.getString("endTime");
						plateOld		 =	orderObj.getString("plateOld");
						plate			 =	orderObj.getString("plate");
						state			 =  "0";
		
						//	进行表结构的检查;
						//	检查加油统计表,检查加油车牌临时表;
						checkTable(baseUtils, stationid);
						
						//	进行数据加入的操作;
						sql		 		 =  
						"insert into tongji_"+stationid+"_jiayou (jiayou_id,orderNum,price,quantity,money,payType,cardNum,oilGunNum,startTime,endTime,plateOld,plate,state) values("+ 
						"'"+jiayou_id+"','"+orderNum+"','"+price+"','"+quantity+"','"+money+"',"+payType+",'"+cardNum+"','"+oilGunNum+"','"+startTime+"','"+endTime+"','"+plateOld+"','"+plate+"',"+state+")";
						
						//	操作结果;
						int 		oper =  baseUtils.update(sql);
						
						//	结果内容;
						if(oper!=0) {
							
							errCode	=	"0";
							result	=	"OK";
							tag		= 	"result";
						}
					}else
						result				= "查询结果为空";
				}else
					result				= "查询失败";
			}else
				result		= "查询结果为空";
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		back.put("errorCode", errCode);
		back.put(tag, result);
			
		//	结果返回的内容信息;
		response.getWriter().append(back.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	//	检测当前的加油单表;
	private void checkTable(DBaseUtils baseUtils,String stationid) {
		//	数据库的参数;
		String 				dbname	 = "sinopec";
		//	加油表的名称;
		String  			tablename= "tongji_"+stationid+"_jiayou";
		
		//  操作的SQl语句;
        String              sql      = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+dbname+"' and TABLE_NAME='"+tablename+"'";

        //  查询结果的返回值;
        ArrayList<String[]> list     = baseUtils.select(sql);

        //  当表单不存在的情况下;
        if(list.size()==0){
            //  进行表单的创建;
            sql="create table "+tablename+" (" +
                "autoid bigint primary key auto_increment," +
                "jiayou_id bigint," +
                "orderNum varchar(50)," +
                "price varchar(50)," +
                "quantity varchar(50),"+ 
                "money varchar(50),"+ 
                "payType int,"+ 
                "cardNum varchar(20)," +
                "oilGunNum varchar(20)," +
                "startTime varchar(20)," +
                "endTime varchar(20)," +
                "plateOld varchar(20)," +
                "plate varchar(20),"+ 
                "state int)";
            //  进行表单的操作;
            baseUtils.update(sql);
        }
	}

}
