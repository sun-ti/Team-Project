package service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.Util_Net;

import model.DBaseUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//	查询车牌信息;
public class Computer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//	时间区域的标记;
	//	设置时间间隔位10分钟;
	private int    minute	=	10;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Computer() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//	获取的参数内容;
		String 	   parameters= null;
		//	获取的参数对象;
		JSONObject object    = null;
		String 	   clientIp	 = null;
		String 	   oilGunNum = null;
		//	数据库的返回值;
		boolean    flag		 = false;		

		JSONArray   array	 = null;
		//	SQL查询语句;
		String 	    sql		 = "";
		
		//	结果标签;
		String 	    tag		 = "errorDesc";
		//	返回编码;
		String 	    errCode	 = "1";
		//	返回结果;
		String 	    result	 = "数据库连接失败";
		
		
		//	网路信息的开头设置;
		Util_Net    util_Net = new Util_Net(request,response);
		util_Net.setHttpServletParameter();
		
		//	进行数据库操作;
		DBaseUtils  baseUtils= new DBaseUtils();

		//	进行数据库的开启操作;
		flag				 = baseUtils.open();

		JSONObject  back	 = new JSONObject();

		if(flag) {
			
			//	异常处理;
			try {
				
				//	方法的解析;
				System.out.println("实现getEntrancePlates");
				//	获得参数信息的内容;
				parameters		 = request.getParameter("parameters").toString().trim();
				
				//	参数结果的重新初始化;
				result			 = "参数传递失败";				
				//	获取相应的数据信息并且进行过滤;
				if(parameters!=null&&!parameters.equals("")) {

					//	判断参数中是否有相同的内容变量;
					parameters		 = baseUtils.checkInputCode(parameters);
					
					//	对进行过滤之后的数据进行参数的获取;
					object			 = JSONObject.fromObject(parameters);
					
					if(object.has("clientIp")&&object.has("oilGunNum")) {
						
						//	获取油机的IP地址;
						clientIp		 = object.getString("clientIp");
						
						//	获取油机的编号;
						oilGunNum		 = object.getString("oilGunNum");
										
						//	1.根据上传的加油机的ip地址查询出所在的stationid;
						sql				 = "select distinct a.stationid from zhandian_jiayouji a,shebei_jiayouji b where a.jiayouji_id=b.jiayouji_id and b.jiayouji_ip='"+clientIp+"'";
						array			 = baseUtils.select2(sql);
						
						//	查询出了相应的站点的结果;
						if(array.size()!=0) {
							
							//	获得当前的站点结果;
							String stationid = array.getJSONObject(0).getString("stationid").toString().trim();
							System.out.println("站点编号:"+stationid+" 油机的编号:"+oilGunNum);

							//	获得当前的时间;
							long     t2		 =	System.currentTimeMillis();
							long   	 dis	 =	minute*60*1000L;
							long	 today1	 =	t2-60*60*1000L;
							
							//	当前值向前-10分钟,获得现在的站点值;
							long   	 t1	 	 =	t2-dis;
							//	从监控表中获取相应的数据信息;
							sql				 =	"select distinct a.plate from tongji_"+stationid+"_jiankong a ,shebei_shexiangtou b, shebei_jiayouji c, jiayouji_shexiangtou d where a.deviceip=b.shexiangtou_ip and c.jiayouji_id=d.jiayouji_id and b.shexiangtou_id=d.shexiangtou_id and c.jiayouji_ip='"+clientIp+"' and a.plate!='无' and a.jiankong_id between "+t1+" and "+t2;
							
							//	进行查询的内容;
							array			 =  baseUtils.select2(sql);
							
							JSONObject 	obj	 =  new JSONObject();
							
							if(array.size()!=0) {
								
								//	查重数据的数据列表信息;
								JSONArray		items	=	new JSONArray();
								//	进行数据结果的遍历;
								for(int i=0;i<array.size();i++) {

									//	当前的车牌;
									String 				plates 	= 	array.getJSONObject(i).getString("plate").trim();

									//	只在当天的加油信息;
									sql					    	= 	"select count(*) from tongji_"+stationid+"_jiayou where plate='"+plates+"' and jiayou_id between "+today1+" and "+t2+" order by jiayou_id desc ";

									//	获得当前的数据结果集合;
									ArrayList<String[]> list	= 	baseUtils.select(sql);
									String 				temp	=	list.get(0)[0];
									int 				count0	=	Integer.parseInt(temp);
									
									//	加油机上没出现;
									if(count0==0) {
										
										//	当加油机上没出现,但是在出口车牌上出现;
										sql							=	"select count(*) from tongji_"+stationid+"_jiankong a, shebei_shexiangtou b where a.deviceip=b.shexiangtou_ip and b.shexiangtou_fun like '%出口%' and a.plate='"+plates+"' and a.jiankong_id between "+today1+" and "+t2+" order by a.jiankong_id desc ";
										
										list						= 	baseUtils.select(sql);
										temp						=	list.get(0)[0];
										int 				count1	=	Integer.parseInt(temp);
										
										//	查询出相应的出口出现过;
										if(count1==0) {
											
											//	进行添加;
											items.add(plates);	
										}
									}
								}
								//	进行相应的数据返回值;
								obj.put("plates", items.toString());
								//	结果的标志位数;
								errCode				= "0";
								tag					= "result";
								//	将结果进行返回;
								result				= obj.toString();
								
							}else
								result	= "目前车道无车辆";
						}else
							result	= "匹配站点失败";
					}else
						result	= "提交参数失败";
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			//	关闭数据库;
			baseUtils.close();
		}

		back.put("errorCode", errCode);
		back.put(tag, result);

		//	结果返回给前端页面中;
		response.getWriter().append(back.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
