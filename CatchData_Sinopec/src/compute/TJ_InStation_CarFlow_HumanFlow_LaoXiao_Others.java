package compute;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import model.DBaseUtil;
import model.Util;

//	对入占率进行相应的数据信息的统计;
public class TJ_InStation_CarFlow_HumanFlow_LaoXiao_Others extends Util{
	//	数据库的相应内容;
	private DBaseUtil baseUtil;
	
	//	进站的车辆数目;
	private String amount_car_in_station="0";
	private String quantity_xiaoshou_zong;

	//	构造函数;
	public TJ_InStation_CarFlow_HumanFlow_LaoXiao_Others(DBaseUtil baseUtil) {
		this.baseUtil=baseUtil;
	}
	
	/*	tablename:监控表名称;
	 *	t1:第一个时间戳;
	 *  t2:第二个时间戳;
	 * */
	public void compute(String tablename,String[] times) {
		//	时间跨度-初始时间;
		String t1		=	times[0];
		//	时间跨度-终止时间;
		String t2		=	times[1];
		
		//	统计当天的时间-年;
		String year		=	times[2];
		//	统计当天的时间-月;
		String month	=	times[3];
		//	统计当天的时间-日;
		String day		=	times[4];

		//	站点编号->;
		String stationid= 	tablename.substring(tablename.indexOf("_")+1,tablename.lastIndexOf("_"));
		
		//	检测对应的表单是否存在;
		checkTableExist(stationid);
		//	1.统计-车流、人流、加油(汽油、柴油);
		tongji_CarFlow_HumanFlow(stationid, t1, t2, year, month, day);
		
		//	2.统计-劳效->
		tongji_LaoXiao(stationid, year, month, day);
		
		//	3.统计-更新用户画像-总公司->;
		tongji_update_huaxiang_zonggongsi(stationid,t1,t2,year,month,day);

		//	6.统计-更新用户画像-分站点->;
		tongji_update_huaxiang_bystationid(stationid,t1,t2,year,month,day);
		
		//	4.统计-用户级别更新-总公司+分站点->;
		update_clientLevel(stationid);

		//	5.统计-用户分类统计->;
		tongji_client_fenlei(stationid,t1,t2,year,month,day);
	}
	
	//	检查表是否存在;
	private void checkTableExist(String stationid){
			String 				dbname	 = baseUtil.getDb_name();
			String 				tablename= "";
			String              sql      = "";
			ArrayList<String[]> list     = null;
			
			//	检测统计表是否存在;
			tablename					 = "tongji_"+stationid+"_count";
	        sql							 = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+dbname+"' and TABLE_NAME='"+tablename+"'";
	        list						 = baseUtil.select(sql);

	        //  当表单不存在的情况下;
	        if(list.size()==0){
	            //  进行表单的创建;
	            sql="create table "+tablename+" (" +
	                "autoid bigint primary key auto_increment," +
	                "count_id bigint," +
	                "amount_car_out_station bigint," +
	                "amount_car_in_station bigint," +
	                "flow_human_in_station bigint,"+ 
	                "amount_chaiyou varchar(20),"+ 
	                "amount_qiyou varchar(20),"+ 
	                "tongji_time_mean varchar(20),"+
	                "tongji_kehu_fenzhandian varchar(20)," +
	                "tongji_kehu_zonggongsi varchar(20),"+ 
	                "datetime varchar(20)," +
	                "year int," +
	                "month int,"+ 
	                "day int," +
	                "state int)";
	            //  进行表单的操作;
	            baseUtil.update(sql);
	        }
	        
	        //	检测加油表是否存在;
	        tablename		=	"tongji_"+stationid+"_jiayou";
	        sql      		= 	"select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+dbname+"' and TABLE_NAME='"+tablename+"'";
	        list     		= 	baseUtil.select(sql);

	        //  当表单不存在的情况下;
	        if(list.size()==0){
	        	
	            //  进行表单的创建;
	            sql="create table "+tablename+" (" +
	                "autoid bigint primary key auto_increment," +
	                "jiayou_id bigint," +
	                "orderNum varchar(50)," +
	                "price varchar(50)," +
	                "quantity varchar(50)," +
	                "money varchar(50)," +
	                "payType int," +
	                "cardNum varchar(20),"+ 
	                "oilGunNum varchar(20)," +
	                "startTime varchar(20)," +
	                "endTime varchar(20)," +
	                "plateOld varchar(20)," +
	                "plate varchar(20)," +
	                "state int)";
	            //  进行表单的操作;
	            baseUtil.update(sql);
	        }
	        
	        //	检测站点的画像表是否存在;
	        tablename		=	"tongji_"+stationid+"_huaxiang";
	        sql      		= 	"select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+dbname+"' and TABLE_NAME='"+tablename+"'";
	        list     		= 	baseUtil.select(sql);
	        
	        //  当表单不存在的情况下;
	        if(list.size()==0){
	        	
	            //  进行表单的创建;
	            sql=
        		"create table tongji_"+stationid+"_huaxiang("+
				"autoid bigint primary key auto_increment,"+
				"huaxiang_id bigint,"+
				"plate varchar(20),"+
				"oil_type varchar(20),"+
				"day_mean_addoil varchar(20),"+
				"amount_mean_single varchar(20),"+
				"pay_type varchar(20),"+
				"first_station varchar(20),"+
				"first_time_jiayou varchar(20),"+
				"lfirst_time_jiayou bigint,"+
				"last_station varchar(20),"+
				"last_time_jiayou varchar(20),"+
				"llast_time_jiayou bigint,"+
				"num_total_jiayou int(11),"+ 
				"num_total_jinzhan int(11),"+
				"state int"+
				")";
	            //  进行表单的操作;
	            baseUtil.update(sql);
	        }
	        
	        //	检测总站的画像表是否存在;
	        tablename		=	"tongji_zhandian_huaxiang";
	        sql      		= 	"select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+dbname+"' and TABLE_NAME='"+tablename+"'";
	        list     		= 	baseUtil.select(sql);
	        
	        //  当表单不存在的情况下;
	        if(list.size()==0){
	        	
	            //  进行表单的创建;
	            sql=
        		"create table tongji_zhandian_huaxiang("+
				"autoid bigint primary key auto_increment,"+
				"huaxiang_id bigint,"+
				"plate varchar(20),"+
				"oil_type varchar(20),"+
				"day_mean_addoil varchar(20),"+
				"amount_mean_single varchar(20),"+
				"pay_type varchar(20),"+
				"first_station varchar(20),"+
				"first_time_jiayou varchar(20),"+
				"lfirst_time_jiayou bigint,"+
				"last_station varchar(20),"+
				"last_time_jiayou varchar(20),"+
				"llast_time_jiayou bigint,"+
				"num_total_jiayou int(11),"+ 
				"num_total_jinzhan int(11),"+
				"state int"+
				")";
	            //  进行表单的操作;
	            baseUtil.update(sql);
	        }
	        
	        //	管理站点-客户-类型对应表;
	        //	检测站点的画像表是否存在;
	        tablename		=	"guanli_"+stationid+"_kehu_type";
	        sql      		= 	"select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+dbname+"' and TABLE_NAME='"+tablename+"'";
	        list     		= 	baseUtil.select(sql);
	        
	        //  当表单不存在的情况下;
	        if(list.size()==0){
	        	
	            //  进行表单的创建;
	            sql=
        		"create table guanli_"+stationid+"_kehu_type ("+
				"autoid bigint primary key auto_increment,"+
				"plate varchar(20),"+
				"type_id int,"+
				"state int"+
				")";
	            //  进行表单的操作;
	            baseUtil.update(sql);
	        }
	}
	
	//	统计当前的车辆信息表内容;
	private void tongji_CarFlow_HumanFlow(String stationid,String t1,String t2,String year,String month,String day) {
		String date					 = year+"-"+month+"-"+day;
		String amount_car_out_station= "0";
		String flow_human_in_station = "0";
		String amount_qiyou			 = "0";
		String amount_chaiyou		 = "0";
		String tongji_time_mean		 = "0";
		//	SQL语句;
		String sql					 = "";
		
		//	承装查询数据结果的集合;
		ArrayList<String[]> list	 = null;
		//	进出站车辆个数-实际;
		int 				count	 = 0;
		//	进出站车辆时间-实际;
		double				sumTime	 = 0;
		
		//	根据站点进行监控表的表名拼接;
		String jiankong			 	 = "tongji_"+stationid+"_jiankong";
		//	目标表单-名称:tongji_id_count形式建立表单;
		String tongji			 	 = "tongji_"+stationid+"_count";
		
		//	1.统计临街摄像头的车牌数目;
		sql					 		 = "select count(distinct a.plate) from "+jiankong+" a , shebei_shexiangtou b , zhandian_shexiangtou c where a.deviceip=b.shexiangtou_ip and b.shexiangtou_id=c.shexiangtou_id and c.stationid='"+stationid+"' and b.kind="+TAG_KIND5+" and b.shexiangtou_fun like '%车流统计%' and a.plate!='无' and a.jiankong_id between "+t1+" and "+t2;
		list	 					 = baseUtil.select(sql);
		if(list.size()!=0) {
			amount_car_out_station	 = list.get(0)[0];	
		}
		System.out.println("临街车辆:"+amount_car_out_station+"辆");
		//	2.统计入口摄像头的集合;
		sql							 = "select count(distinct a.plate) from "+jiankong+" a , shebei_shexiangtou b , zhandian_shexiangtou c where a.deviceip=b.shexiangtou_ip and b.shexiangtou_id=c.shexiangtou_id and c.stationid='"+stationid+"' and b.kind="+TAG_KIND1+" and b.shexiangtou_fun like '%入口%' and a.plate!='无' and a.jiankong_id between "+t1+" and "+t2;
		list						 = baseUtil.select(sql);
		if(list.size()!=0) {
			amount_car_in_station 	 = list.get(0)[0];	
		}
		System.out.println("入口进站:"+amount_car_in_station+"辆");
		//	3.统计入口摄像头的不重复的车牌;
		sql							 = "select distinct a.plate from "+jiankong+" a , shebei_shexiangtou b , zhandian_shexiangtou c where a.deviceip=b.shexiangtou_ip and b.shexiangtou_id=c.shexiangtou_id and c.stationid='"+stationid+"' and b.kind="+TAG_KIND1+" and b.shexiangtou_fun like '%入口%' and a.plate!='无' and a.jiankong_id between "+t1+" and "+t2;
		list						 = baseUtil.select(sql);
		
		//	不重复的相应数据;
		if(list.size()!=0) {
			//	对不重复的车牌进行数据统计;
			for(String[] items:list) {
				
				//	车牌-不重复;
				String item=items[0];
				//	获取同车牌入站时间;
				sql						 	= "select a.jiankong_id from "+jiankong+" a,shebei_shexiangtou b,zhandian_shexiangtou c where a.deviceip=b.shexiangtou_ip and b.shexiangtou_id=c.shexiangtou_id and c.stationid='"+stationid+"' and b.kind="+TAG_KIND1+" and b.shexiangtou_fun like '%入口%' and a.plate='"+item+"' and a.jiankong_id between "+t1+" and "+t2+" limit 0,1";
				ArrayList<String[]> list1	= baseUtil.select(sql);
				
				//	获取同车牌出站时间;
				sql						 	= "select a.jiankong_id from "+jiankong+" a,shebei_shexiangtou b,zhandian_shexiangtou c where a.deviceip=b.shexiangtou_ip and b.shexiangtou_id=c.shexiangtou_id and c.stationid='"+stationid+"' and b.kind="+TAG_KIND1+" and b.shexiangtou_fun like '%出口%' and a.plate='"+item+"' and a.jiankong_id between "+t1+" and "+t2+" order by a.jiankong_id desc limit 0,1";
				ArrayList<String[]> list2	= baseUtil.select(sql);

				if(list1.size()!=0&&list2.size()!=0) {
					//	时间戳的初始;
					String 			temp 	= list1.get(0)[0];
					double			start	= Double.parseDouble(temp);
					
					//	时间戳的终止;
					temp 				 	= list2.get(0)[0];
					double			end	 	= Double.parseDouble(temp);
				
					double			chazhi	= end-start;
					sumTime				   += chazhi;
					count++;
				}
			}
		}

		//	统计平均在站点时间,以分钟为单位;
		if(count!=0)
			tongji_time_mean			= baseUtil.getDataAccordingToByte(((sumTime/count)/1000)/60, 2);
		
		System.out.println("实际平均时间:"+tongji_time_mean+"分钟");
		
		//	4.人脸识别的摄像头监控;
		sql							 = "select count(*) from "+jiankong+" a , shebei_shexiangtou b , zhandian_shexiangtou c where a.deviceip=b.shexiangtou_ip and b.shexiangtou_id=c.shexiangtou_id and c.stationid='"+stationid+"' and b.kind="+TAG_KIND3+" and b.shexiangtou_fun like '%人脸%' and a.jiankong_id between "+t1+" and "+t2;
		list						 = baseUtil.select(sql);
		if(list.size()!=0) {
			flow_human_in_station 	 = list.get(0)[0];	
		}
		
		//	5.根据加油卡获得相应的加油量——汽油、柴油;
		sql							 = "select sum(a.LITTER) from zcun_oildetail a, station b , jy_oil_type c where a.NODENO=b.nodeno and b.stationid='"+stationid+"' and a.OILNO=c.oil_id and c.oil_kind='汽油' and a.ACCOUNTDATE like '%"+date+"%'";
		list						 = baseUtil.select(sql);

		if(list.size()!=0) {
			
			amount_qiyou			 = list.get(0)[0];
			//	保留小数点后3位;
			amount_qiyou			 = getDataAccordingToByte(amount_qiyou, 3);	
		}
		
		//	6.
		sql							 = "select sum(a.LITTER) from zcun_oildetail a,station b,jy_oil_type c where a.NODENO=b.nodeno and b.stationid='"+stationid+"' and a.OILNO=c.oil_id and c.oil_kind='柴油' and a.ACCOUNTDATE like '%"+date+"%'";
		list						 = baseUtil.select(sql);
		
		if(list.size()!=0) {
			amount_chaiyou			 = list.get(0)[0];
			//	保留小数点后3位;
			amount_chaiyou		 	 = getDataAccordingToByte(amount_chaiyou, 3);	
		}
		
		//	石油的销售的总量;
		quantity_xiaoshou_zong		 = getDataAccordingToByte((Double.parseDouble(amount_qiyou)+Double.parseDouble(amount_chaiyou)), 3);

		//	将相应的统计结果放入到数据表中;	
		long 				lnow	 =	System.currentTimeMillis();
		String				datetime =	baseUtil.getCurrentDatetime(lnow, TimeFormat);

		sql							 = "insert into "+tongji+" ("+ 
		"count_id,amount_car_out_station,amount_car_in_station,flow_human_in_station,amount_chaiyou,amount_qiyou,tongji_time_mean,datetime,year,month,day,state) values("+ 
		""+lnow+","+amount_car_out_station+","+amount_car_in_station+","+flow_human_in_station+",'"+amount_chaiyou+"','"+amount_qiyou+"','"+tongji_time_mean+"','"+datetime+"',"+year+","+month+","+day+",0)";
		
		baseUtil.update(sql);
		
	}
	
	//	计算当前的劳效;
	private void tongji_LaoXiao(String stationid,String year,String month,String day) {
		String 		sql				=	"";
		ArrayList<String[]> list	=	null;
		
		String 		snum_morning	=	null;
		String 		snum_noon		=	null;
		String 		snum_night		=	null;
		String 		num_zaigang		=	null;
		
		//	SQl查询的内容;
		sql							= 	"select a.num_morning,a.num_noon,a.num_night from jy_zhandian_zaigang a,zhandian_zaigang b where a.zaigang_id=b.zaigang_id and b.stationid='"+stationid+"' and a.year="+year+" and a.month="+month+" and a.day="+day +" order by a.zaigang_id desc limit 0,1";
		list						=	baseUtil.select(sql);
		//	排班-正常填写的情况;
		if(list.size()!=0) {
			
			String[] 	items		=	list.get(0);

			//	查询出当天的进站数;
			snum_morning	=	items[0];
			snum_noon		=	items[1];
			snum_night		=	items[2];
			
		}
		//	排班-不填写的情况;
		else {
			snum_morning	=	"1";
			snum_noon		=	"1";
			snum_night		=	"1";
		}
		
		//	在岗总数的统计;
		num_zaigang					=	(Integer.parseInt(snum_morning)+Integer.parseInt(snum_noon)+Integer.parseInt(snum_night))+"";
		
		//	数据的插入;
		long   lnow					=	System.currentTimeMillis();

		//	进站的总车牌数目;	
		String num_chepai			=	amount_car_in_station;
		
		//	当前的字符串时间;
		String datetime				=	baseUtil.getCurrentDatetime(lnow, TimeFormat);
		//	具有站点-劳效的对应表;
		sql							=   
		"insert into jy_zhandian_laoxiao"+ 
		"(laoxiao_id,num_zaigang,num_chepai,quantity_xiaoshou,datetime,year,month,day,state) values("+ 
		""+lnow+","+num_zaigang+","+num_chepai+",'"+quantity_xiaoshou_zong+"','"+datetime+"',"+year+","+month+","+day+",0)";
		
		//	数据的更新;
		baseUtil.update(sql);
		//	将站点与劳效的id编号进行对应;
		sql							=   "insert into zhandian_laoxiao(stationid,laoxiao_id,state) values('"+stationid+"',"+lnow+",0)";
		baseUtil.update(sql);
	}
	
	//	对客户类型进行画像统计-根据站点;
	private void tongji_update_huaxiang_bystationid(String stationid,String t1,String t2,String year,String month,String day) {
		System.out.println("进行根据站点进行画像统计");
		ArrayList<String[]> list =	null;
		
		String    pay_type			=	"";
		
		//	进行数据的插入;
		//	当前的毫秒数;
		long   	  lnow				=	System.currentTimeMillis();
		String 	  huaxiang_id		=	lnow+"";
		
		//	plate-加油车牌的内容;
		String    oil_type			=	"";
		String    day_mean_addoil	=	"0";
		
		String    amount_mean_single=	"0";
		
		//	当前站点;
		String 	  first_station		=	stationid;
		
		//	第一次加油的时间;
		String 	  first_time_jiayou	=	baseUtil.getCurrentDatetime(lnow, "yyyy-MM-dd HH:mm:ss");
		String    lfirst_time_jiayou=	lnow+"";
		
		//	第2次加油的时间;
		String 	  last_station		=	stationid;
		String 	  last_time_jiayou	=	baseUtil.getCurrentDatetime(lnow, "yyyy-MM-dd HH:mm:ss");
		
		String    llast_time_jiayou	=	lnow+"";
		
		String    num_total_jiayou	=	"0";
		String    num_total_jinzhan	=	"1";
		
		String    			state	=	"0";
		String 				date	=	year+"-"+month+"-"+day;
		
		//	根据站点对用户的画像进行统计;
		String 		     jiankong 	=  "tongji_"+stationid+"_jiankong";
		String 				sql		=	"";
		sql						 	=  "select a.plate from "+jiankong+" a,shebei_shexiangtou b,zhandian_shexiangtou c where a.deviceip=b.shexiangtou_ip and b.shexiangtou_id=c.shexiangtou_id and c.stationid='"+stationid+"' and b.kind="+TAG_KIND1+" and b.shexiangtou_fun like '%入口%' and plate!='无' and a.jiankong_id between "+t1+" and "+t2+" order by jiankong_id";
		list 					 	=	baseUtil.select(sql);
		
		//	车牌结果集合-不重复
		Set<String>     	sets 	=  new TreeSet<String>();
		
		//	车牌结果结合-长度;
		if(list.size()!=0) {	
			for(String[] items:list) {
				String 	   plate =  items[0];
				sets.add(plate);
			}
		}
		
		//	当天的车牌列表;
		System.out.println("车牌共有:"+list.size()+"|不重复的车牌有:"+sets.size());
		
		//	将站点画像放置在列表中;
		String 			huaxiang =	"tongji_"+stationid+"_huaxiang";
		String 			jiayou	 =	"tongji_"+stationid+"_jiayou";
		
		//	当天的车牌列表;
		for(String plate:sets) {
			
			sql			=	"select count(*) from "+huaxiang+" where plate='"+plate+"'";
			list		=	baseUtil.select(sql);
			
			//	当列表不为空的情况下;
			if(list!=null) {
				
				//	结果的临时存储位置;
				String temp  	= 	list.get(0)[0];
				int    count 	= 	Integer.parseInt(temp);
				
				//	判断根据站点的用户画像的内容;
				//	当结果不为0的情况下;
				if(count!=0) {
					
					//	从已有的用户信息画像中取出相应的数据信息;
					sql			   	 			   = "select amount_mean_single,lfirst_time_jiayou,num_total_jiayou,num_total_jinzhan from "+huaxiang+" where plate='"+plate+"' limit 0,1";
					list		    			   = baseUtil.select(sql);
					
					if(list.size()!=0) {
						
						String[] 			items  	   = list.get(0);
						
						//	单次加油的平均量;
						String  amount_mean_single_old = items[0];
						double  amount_old			   = Double.parseDouble(amount_mean_single_old);
						
						//	第一次加油的时间;
						String  first_time_jiayou_old  = items[1];

						//	一共进行加油的次数;
						String  num_total_jiayou_old   = items[2];
						int	    count_total_jiayou	   = Integer.parseInt(num_total_jiayou_old);

						//	一共进站的次数;
						String  num_total_jinzhan_old  = items[3];
						int	    count_total_jinzhan	   = Integer.parseInt(num_total_jinzhan_old);
						
						//	新生成的油量;
						String   amount_single_new	   = "0";
						double   amount_new			   = 0;
						
						//	差值-以天数为单位;
						double   dis				   = 24*60*60*1000;
						
						//	中间参数;
						double   sum_temp		  	   = 0;
						
						sql				=	"select quantity,payType,startTime,endTime from "+jiayou+" where plate='"+plate+"' and startTime like '%"+date+"%' limit 0,1";
						list			=	baseUtil.select(sql);
						
						if(list.size()!=0) {
							items				=	list.get(0);
							
							amount_single_new	=	items[0];
							
							//	将数据进行过滤数据单位;
							if(amount_single_new.contains("升")) {
								
								//	数据进行相应的操作;
								amount_single_new=amount_single_new.substring(0,amount_single_new.indexOf("升"));
							}else if(amount_single_new.contains("毫升")) {
								
								//	数据进行相应的操作;
								amount_single_new=amount_single_new.substring(0,amount_single_new.indexOf("毫升"));
							}
							
							//	将新的单次加油量进行赋值;
							amount_new=Double.parseDouble(amount_single_new);
						}

						//	进行数据的过渡处理;
						//	1.加油-单次平均的量;
						//	第一次加油的情况下;
						sum_temp			 = (amount_old*count_total_jiayou)+amount_new;
						
						if(list.size()!=0)
							//	总的次数加1;
							count_total_jiayou	+=	1;
						
						count_total_jinzhan +=	1;
						
						if(count_total_jiayou!=0)
							//	新-单次加油的平均量;
							amount_mean_single	= 	getDataAccordingToByte((sum_temp/count_total_jiayou),5);
						
						//	2.加油-平均时间间隔;
						sum_temp			 =  (Double.parseDouble(llast_time_jiayou)-Double.parseDouble(first_time_jiayou_old))/Double.parseDouble(dis+"");
						
						//	平均加油的天数;
						//	当分母不等于0的情况下;
						if(count_total_jinzhan!=0)
							//	新-平均入站时间间隔;	
							sum_temp		 =  Double.parseDouble(getDataAccordingToByte(sum_temp,5))/count_total_jinzhan;
						
						//	加油的平均天数;
						day_mean_addoil		 =  getDataAccordingToByte(sum_temp, 5);
						
						//	根据车牌进行用户画像的修改;
						sql="update "+huaxiang+" set "
						  + "day_mean_addoil='"+day_mean_addoil+"' ,"
						  + "amount_mean_single='"+amount_mean_single+"',"
						  + "last_station='"+stationid+"',"
						  + "last_time_jiayou='"+last_time_jiayou+"',"
						  + "llast_time_jiayou="+llast_time_jiayou+","
						  + "num_total_jiayou="+count_total_jiayou+","
						  + "num_total_jinzhan="+count_total_jinzhan+" where plate='"+plate+"'";
						
						//	进行数据更新的操作;
						baseUtil.update(sql);
					}
				}
				//	当结果为0的情况下;
				else {
					
					//	从当前站点-加油机获取对应的加油信息
					sql				=	"select quantity,payType from "+jiayou+" where plate='"+plate+"' and startTime like '%"+date+"%' limit 0,1";
					list			=	baseUtil.select(sql);
					
					//	确实进行过加油;
					if(list.size()!=0) {
						
						//	数据结果的数组;
						String [] items				=	list.get(0);
						
						//	加油的量数;
						String 	  quantity			=	items[0];
						
						//	加油量获取出相应的数值内容;
						if(quantity.contains("升")) {
							
							quantity	=	quantity.substring(0,quantity.indexOf("升"));
						
						}else if(quantity.contains("毫升")) {
							
							quantity	=	quantity.substring(0,quantity.indexOf("毫升"));
						}
						
						pay_type					=	items[1];

						//	plate-加油车牌的内容;
						oil_type					=	"";
						day_mean_addoil				=	"第1次加油";
						
						amount_mean_single			=	getDataAccordingToByte(quantity, 2);

						num_total_jiayou			=	"1";
					}
					num_total_jinzhan	=	"1";
					
					//	向画像表单进行插入;
					sql="insert into "+huaxiang
					  + " (huaxiang_id,plate,oil_type,day_mean_addoil,amount_mean_single,pay_type,first_station,first_time_jiayou,lfirst_time_jiayou,last_station,last_time_jiayou,llast_time_jiayou,num_total_jiayou,num_total_jinzhan,state) values("
					  + ""+huaxiang_id+",'"+plate+"','"+oil_type+"','"+day_mean_addoil+"','"+amount_mean_single+"','"+pay_type+"',"+ "'"+first_station+"','"+first_time_jiayou+"',"+lfirst_time_jiayou+","+ "'"+last_station+"','"+last_time_jiayou+"',"+llast_time_jiayou+","+num_total_jiayou+","+num_total_jinzhan+","+state+")";

					baseUtil.update(sql);
				}
			}
		}
	}
	
	//	对客户类型进行进行统计;
	private void tongji_update_huaxiang_zonggongsi(String stationid,String t1,String t2,String year,String month,String day) {
		String 		  tablename	    =  "tongji_"+stationid+"_jiankong";
		//	检测当前的加油表是否存在?;
		String 		  jiayou	    =  "tongji_"+stationid+"_jiayou";
		//	进行数据的插入;
		//	当前的毫秒数;
		long   	  	  lnow		    =  System.currentTimeMillis();
		String 	  	  huaxiang_id   =  lnow+"";
		String    	  pay_type	    =  "";
		String    	  oil_type	    =  "";
		
		String    day_mean_addoil	=  "0";
		
		String    amount_mean_single=  "0";
		
		//	当前站点;
		String 	  first_station		=  stationid;
		
		//	第一次加油的时间;
		String 	  first_time_jiayou	=  baseUtil.getCurrentDatetime(lnow, "yyyy-MM-dd HH:mm:ss");
		String    lfirst_time_jiayou=  lnow+"";
		
		//	第2次加油的时间;
		String 	  last_station		=  stationid;
		String 	  last_time_jiayou	=  baseUtil.getCurrentDatetime(lnow, "yyyy-MM-dd HH:mm:ss");
		String    llast_time_jiayou	=  lnow+"";
		
		String    num_total_jiayou	=  "0";
		String    num_total_jinzhan	=  "0";
		
		
		String    	  state		    =  "0";
		String 	  	  date			=  year+"-"+month+"-"+day;
		
		//	遍历当天的所有的车牌-从监控表;
		String 				sql	  =	"";
		ArrayList<String[]> list  =	null;
		
		sql						  = "select distinct a.plate from "+tablename+" a,shebei_shexiangtou b,zhandian_shexiangtou c where a.deviceip=b.shexiangtou_ip and b.shexiangtou_id=c.shexiangtou_id and c.stationid='"+stationid+"' and b.kind="+TAG_KIND1+" and b.shexiangtou_fun like '%入口%' and plate!='无' and a.jiankong_id between "+t1+" and "+t2+" order by jiankong_id";
		list					  = baseUtil.select(sql);

		//	总公司画像结果;
		for(String[] plates:list) {
			
			//	车牌内容;
			String plate=plates[0];
			// 判断当前车牌是否存在于用户画像中;
			sql		=	"select count(*) from tongji_zhandian_huaxiang where plate='"+plate+"'";
			// 查询结果的列表;
			list	=	baseUtil.select(sql);
			
			// 查询的是统计结果;
			if(list!=null) {
				//	结果的临时存储位置;
				String temp  	= 	list.get(0)[0];
				//	查询出的结果标记;
				int    count 	= 	Integer.parseInt(temp);

				//	已有用户画像,进行用户更新;
				if(count!=0) {

					//	从已有的用户画像中抽取,只可能一个车牌一个账户;
					sql			   	 			   = "select amount_mean_single,lfirst_time_jiayou,num_total_jiayou,num_total_jinzhan from tongji_zhandian_huaxiang where plate='"+plate+"' limit 0,1";
					list		    			   = baseUtil.select(sql);
					String[] 			items  	   = list.get(0);
					
					//	单次加油的平均量;
					String  amount_mean_single_old = items[0];
					double  amount_old			   = Double.parseDouble(amount_mean_single_old);
				
					//	第一次加油的时间;
					String  lfirst_time_jiayou_old  = items[1];

					//	一共进行加油的次数;
					String  num_total_jiayou_old   = items[2];
					int	    count_total_jiayou	   = Integer.parseInt(num_total_jiayou_old);

					//	一共进站的次数;
					String  num_total_jinzhan_old  = items[3];
					int	    count_total_jinzhan	   = Integer.parseInt(num_total_jinzhan_old);
					
					//	新生成的油量;
					String   amount_single_new	   = "0";
					double   amount_new			   = 0;
					
					//	差值-以天数为单位;
					double   dis				   = 24*60*60*1000;
					
					//	中间参数;
					double   sum_temp		  	   = 0;

					//	从相应的站点加油表中获取到相应的数据信息;
					sql				=	"select quantity,payType,startTime,endTime from "+jiayou+" where plate='"+plate+"' and startTime like '%"+date+"%' order by jiayou_id desc limit 0,1";

					//	结果数据集合;
					list			=	baseUtil.select(sql);
					
					//	进行新一次加油;
					if(list.size()!=0) {
						items				=	list.get(0);
						
						amount_single_new	=	items[0];
						
						//	将数据进行过滤数据单位;
						if(amount_single_new.contains("升")) {
							
							//	数据进行相应的操作;
							amount_single_new=amount_single_new.substring(0,amount_single_new.indexOf("升"));
						}else if(amount_single_new.contains("毫升")) {
							
							//	数据进行相应的操作;
							amount_single_new=amount_single_new.substring(0,amount_single_new.indexOf("毫升"));
						}
						
						//	将新的单次加油量进行赋值;
						amount_new	=	Double.parseDouble(amount_single_new);
					}

					//	进行数据的过渡处理;
					//	1.加油-单次平均的量;
					//	计算出此次加油之前的结果;	
					sum_temp			 = (amount_old*count_total_jiayou)+amount_new;

					//	当此次加油成功时;
					if(list.size()!=0)
						//	加油的总次数加1;
						count_total_jiayou	+=	1;	

					//	进站的总次数加1;	
					count_total_jinzhan +=	1;
					
					if(count_total_jiayou!=0)
						//	新-单次加油的平均量;
						amount_mean_single	 = getDataAccordingToByte(((sum_temp/count_total_jiayou)), 2);
					
					//	2.加油-平均时间间隔;
					sum_temp			 =  (Double.parseDouble(llast_time_jiayou)-Double.parseDouble(lfirst_time_jiayou_old))/Double.parseDouble(dis+"");
					
					
					//	平均加油的天数;
					//	当分母不等于0的情况下;
					if(count_total_jinzhan!=0)
						//	新-平均入站时间间隔;	
						sum_temp		 =  Double.parseDouble(getDataAccordingToByte(sum_temp, 5))/count_total_jinzhan;
					
					//	加油的平均天数;
					day_mean_addoil		 =  getDataAccordingToByte(sum_temp, 5);
					
					//	根据车牌进行用户画像的修改;
					sql="update tongji_zhandian_huaxiang set "
					  + "day_mean_addoil='"+day_mean_addoil+"' ,"
					  + "amount_mean_single='"+amount_mean_single+"',"
					  + "last_station='"+stationid+"',"
					  + "last_time_jiayou='"+last_time_jiayou+"',"
					  + "llast_time_jiayou="+llast_time_jiayou+","
					  + "num_total_jiayou="+count_total_jiayou+","
					  + "num_total_jinzhan="+count_total_jinzhan+" where plate='"+plate+"'";
					
					//	进行数据更新的操作;
					baseUtil.update(sql);
				}
				
				//	新增用户画像:当用户画像表不存在时;
				else {
					//	例子中由于加油机的对象只有1个,因此实际的数据只有1个;
					//	根据实际的加油表单查询出相应的加油量+支付方式;
					sql				=	"select quantity,payType from "+jiayou+" where plate='"+plate+"' and startTime like '%"+date+"%' order by jiayou_id desc limit 0,1";

					//	结果数据集合;
					list			=	baseUtil.select(sql);
					
					//	确实进行过加油;
					if(list.size()!=0) {
						
						//	数据结果的数组;
						String [] items				=	list.get(0);
						
						//	加油的量数;
						String 	  quantity			=	items[0];
						
						//	加油量获取出相应的数值内容;
						if(quantity.contains("升")) {
							
							quantity	=	quantity.substring(0,quantity.indexOf("升"));
						
						}else if(quantity.contains("毫升")) {
							
							quantity	=	quantity.substring(0,quantity.indexOf("毫升"));
						}
						
						pay_type		  =	items[1];
											
						//	plate-加油车牌的内容;
						oil_type					=	"";
						
						amount_mean_single  =	getDataAccordingToByte(quantity, 2);
						
						//	进行实际加油的显示;
						num_total_jiayou	=	"1";	
						day_mean_addoil	    =	"第1次加油";
					}
					
					num_total_jinzhan	=	"1";
					//	向画像表单进行插入;
					sql="insert into tongji_zhandian_huaxiang "
					  + "(huaxiang_id,plate,oil_type,day_mean_addoil,amount_mean_single,pay_type,first_station,first_time_jiayou,lfirst_time_jiayou,last_station,last_time_jiayou,llast_time_jiayou,num_total_jiayou,num_total_jinzhan,state) values("
					  + ""+huaxiang_id+",'"+plate+"','"+oil_type+"','"+day_mean_addoil+"','"+amount_mean_single+"','"+pay_type+"',"+ "'"+first_station+"','"+first_time_jiayou+"',"+lfirst_time_jiayou+","+ "'"+last_station+"','"+last_time_jiayou+"',"+llast_time_jiayou+","+num_total_jiayou+","+num_total_jinzhan+","+state+")";

					baseUtil.update(sql);
				}
			}
		}		
	}
	
	//	对用户的级别进行更新;
	private void  update_clientLevel(String stationid) {
		//	遍历所有的用户画像的内容信息;
		//	从现在为止向前推三个月的数据牌设数据;
		ArrayList<String[]> list   = null;
		String 				sql	   = "";
		//	3个月的时间戳;
		long				dis    = 90*24*60*60*1000L;
		double			    dayDis = 24*60*60*1000;
		
		//	当前时间:毫秒数;
		long   				t2 	   = System.currentTimeMillis();
		String 				current= baseUtil.getCurrentDatetime(t2, TimeFormat);
		
		//	3个月前时间:毫秒数;
		long				t1	   = t2-dis;
		
		//	根据画像数据统计;
		sql	 					   = "select huaxiang_id,plate,num_total_jinzhan,lfirst_time_jiayou,llast_time_jiayou from tongji_zhandian_huaxiang where llast_time_jiayou between "+t1+" and "+t2+" order by llast_time_jiayou";
		list 					   = baseUtil.select(sql);
		System.out.println("从"+current+"至之前的3个月中,用户画像表中有:"+list.size()+"个对象");
		//	根据3个月的加油次数来判断等级;
		//	对于数据集合进行遍历;
		for(String[] items:list) {
			
			//	车牌编号;
			String	 plate				=	items[1];
			//	总的加油次数;
			String 	 num_total_jiayou	=	items[2];
			int	   	 num				=	Integer.parseInt(num_total_jiayou);
			
			//	第一次
			String 	 first_time_jiayou	=	items[3];
			String 	 last_time_jiayou	=	items[4];
			
			//	同一辆车的两个时间相互比较:起始与结束之间的时间差:天数;
			double	 t					=	(Double.parseDouble(last_time_jiayou)-Double.parseDouble(first_time_jiayou))/dayDis;
			
			double   dis1				=	Double.parseDouble(getDataAccordingToByte(t, 3));

			t							=	(Double.parseDouble(t2+"")-Double.parseDouble(last_time_jiayou))/dayDis;
			
			//	此辆车末次与当时间相互比较:此时与最终之间的时间差:天数;
			double   dis2				=	Double.parseDouble(getDataAccordingToByte(t, 3));
			
//			System.out.println("车牌:"+plate+" | 同辆车的时间差:"+dis1+"天 | 最后一次出现与当前时间差:"+dis2+"天");
			
			//	此辆车有三个月没来,从客户画像中剔除;	
			if(dis2>90) {
				
				//	设置总的用户画像表里的数据信息;
				sql						=	"update tongji_zhandian_huaxiang set num_total_jiayou=0 where plate='"+plate+"'";
				baseUtil.update(sql);
				
				//	将此车牌的数据从管理-客户-类型的表单中剔除;
				sql						=	"update guanli_kehu_type set type_id=4 where plate='"+plate+"'";
				baseUtil.update(sql);
			}
			//	此车最后一次出现在三个月中;
			else {
				//	此车的第一次与最后一次之间的间隔超过3个月;
				if(dis1>90) {
					//	TODO 数据降级**;
					//	将此车牌的数据从用户画像中删除;
					sql					=	"update tongji_zhandian_huaxiang set num_total_jiayou=0 where plate='"+plate+"'";
					baseUtil.update(sql);
					
					//	将此车牌的数据从管理-客户-类型的表单中剔除;
					sql					=	"update guanli_kehu_type set type_id=4 where plate='"+plate+"'";
					baseUtil.update(sql);					
				}
				//	此车的第一次与最后一次之间的间隔未超3个月;
				else {
					//	将总次数/3个月=每个月;
					double cishu= num/3;
					//	进行数据级别的筛选;
					if(cishu>5) {
						//	层次1级;
						tongji_checkKind(null,plate, 1);
					}
					else if(cishu>2&&cishu<=5) {
						//	层次2级;
						tongji_checkKind(null,plate, 2);
					}
					else if(cishu>1&&cishu<=2) {
						//	层次3级;
						tongji_checkKind(null,plate, 3);
					}
					else {
						//	层次4级;
						tongji_checkKind(null,plate, 4);
					}
				}
			}
		}
		
		//	对分站点的画像列表进行数据查询的方式;
		String 			  huaxiang = "tongji_"+stationid+"_huaxiang";
		String 			  guanli   = "guanli_"+stationid+"_kehu_type";
		sql	 					   = "select huaxiang_id,plate,num_total_jinzhan,lfirst_time_jiayou,llast_time_jiayou from "+huaxiang+" where llast_time_jiayou between "+t1+" and "+t2+" order by llast_time_jiayou";
		list 					   = baseUtil.select(sql);
		System.out.println("从"+current+"至之前的3个月中,'"+stationid+"'用户画像表中有:"+list.size()+"个对象");
		
		//	根据3个月的加油次数来判断等级;
		//	对于数据集合进行遍历;
		for(String[] items:list) {
			
			//	车牌编号;
			String	 plate				=	items[1];
			//	总的加油次数;
			String 	 num_total_jiayou	=	items[2];
			int	   	 num				=	Integer.parseInt(num_total_jiayou);
			
			//	第一次
			String 	 first_time_jiayou	=	items[3];
			String 	 last_time_jiayou	=	items[4];
			
			double	 t					=	(Double.parseDouble(last_time_jiayou)-Double.parseDouble(first_time_jiayou))/dayDis;
			
			double   dis1				=	Double.parseDouble(getDataAccordingToByte(t, 3));

			t							=	(Double.parseDouble(t2+"")-Double.parseDouble(last_time_jiayou))/dayDis;

			//	此辆车末次与当时间相互比较:此时与最终之间的时间差:天数;
			double   dis2				=	Double.parseDouble(getDataAccordingToByte(t, 3));
						
//			System.out.println("车牌:"+plate+" | 同辆车的时间差:"+dis1+"天 | 最后一次出现与当前时间差:"+dis2+"天");
			
			//	此辆车有三个月没来,从客户画像中剔除;	
			if(dis2>90) {
				
				//	设置总的用户画像表里的数据信息;
				sql						=	"update "+huaxiang+" set num_total_jiayou=0 where plate='"+plate+"'";
				baseUtil.update(sql);
				
				//	将此车牌的数据从管理-客户-类型的表单中剔除;
				sql						=	"update "+guanli+" set type_id=4 where plate='"+plate+"'";
				baseUtil.update(sql);
			}
			//	此车最后一次出现在三个月中;
			else {
				//	此车的第一次与最后一次之间的间隔超过3个月;
				if(dis1>90) {
					//	TODO 数据降级**;
					//	将此车牌的数据从用户画像中删除;
					sql					=	"update "+huaxiang+" set num_total_jiayou=0 where plate='"+plate+"'";
					baseUtil.update(sql);
					
					//	将此车牌的数据从管理-客户-类型的表单中剔除;
					sql					=	"update "+guanli+" set type_id=4 where plate='"+plate+"'";
					baseUtil.update(sql);					
				}
				//	此车的第一次与最后一次之间的间隔未超3个月;
				else {
					//	将总次数/3个月=每个月;
					double cishu= num/3;
					//	进行数据级别的筛选;
					if(cishu>5) {
						//	层次1级;
						tongji_checkKind(stationid,plate, 1);
					}
					else if(cishu>2&&cishu<=5) {
						//	层次2级;
						tongji_checkKind(stationid,plate, 2);
					}
					else if(cishu>1&&cishu<=2) {
						//	层次3级;
						tongji_checkKind(stationid,plate, 3);
					}
					else {
						//	层次4级;
						tongji_checkKind(stationid,plate, 4);
					}
				}
			}
		}
		
	}
	//	检测表中的类型内容;
	private void tongji_checkKind(String stationid,String plate,int kind) {
		//	首先查询这个数据有没有;
		//	SQL语句;
		String 				sql		=	"";
		//	查询结果数据集合;
		ArrayList<String[]> list	=	null;
		//	计数的标签;
		int 				count	=	0;
		
		if(stationid==null) {
			sql							=	"select count(*) from guanli_kehu_type where plate='"+plate+"'";
			
			//	查询结果信息;
			list						=	baseUtil.select(sql);
			
			if(list!=null) {
				
				//	数据的历史结果信息;
				String 	temp	=	list.get(0)[0];
				
				//	计数的统计;
				count			=	Integer.parseInt(temp);
				
				//	查询到的结果-修改列表;
				if(count!=0) {
					
					//	进行数据的更新;
					sql			=	"update guanli_kehu_type set type_id="+kind+" where plate='"+plate+"'";
				}
				//	查询不到结果-新增列表;
				else {
					
					//	进行数据的插入;
					sql			=	"insert into guanli_kehu_type (plate,type_id,state) value ('"+plate+"',"+kind+",0)";
				}
				//	更新数据结果信息;
				baseUtil.update(sql);
			}
		}
		//	根据站点进行数据的统计;
		else {
			
			//	进行对应站点客户管理的内容;
			String 		guanli	=	"guanli_"+stationid+"_kehu_type";
			sql					=	"select count(*) from "+guanli+" where plate='"+plate+"'";
			list				=	baseUtil.select(sql);
			
			if(list!=null) {
				
				//	数据的历史结果信息;
				String 	temp	=	list.get(0)[0];
				
				//	计数的统计;
				count			=	Integer.parseInt(temp);
				
				//	查询到的结果-修改列表;
				if(count!=0) {
					
					//	进行数据的更新;
					sql			=	"update "+guanli+" set type_id="+kind+" where plate='"+plate+"'";
				}
				//	查询不到结果-新增列表;
				else {
					
					//	进行数据的插入;
					sql			=	"insert into "+guanli+" (plate,type_id,state) value ('"+plate+"',"+kind+",0)";
				}
				//	更新数据结果信息;
				baseUtil.update(sql);
			}
		}
		
	}

	//	根据客户的类别进行统计;
	private void tongji_client_fenlei(String stationid,String t1,String t2,String year,String month,String day) {
		System.out.println("进行车辆类型的归类!");
		String 					jiankong	=	"tongji_"+stationid+"_jiankong";
		String					tongji		=	"tongji_"+stationid+"_count";
		String 					guanli		=	"guanli_"+stationid+"_kehu_type";
		//	进行统计的SQL语句;
		String 							sql	=	"";
		//	承装数据的容器;
		ArrayList<String[]> 		   list	=	null;
		
		//	进行统计的类型数目;
		String 	count1		=	null,count2=null,count3=null,count4 = 	null;
		
		sql					=	"select count(distinct a.plate) from "+jiankong+" a, guanli_kehu_type b where a.plate=b.plate and b.type_id="+TAG_KIND1_CLIENT+" and a.jiankong_id between "+t1+" and "+t2;
		
		list				=	baseUtil.select(sql);
		
		if(list!=null) {
			count1=list.get(0)[0];
		}
		
		sql					=	"select count(distinct a.plate) from "+jiankong+" a, guanli_kehu_type b where a.plate=b.plate and b.type_id="+TAG_KIND2_CLIENT+" and a.jiankong_id between "+t1+" and "+t2;
		list				=	baseUtil.select(sql);
		
		if(list!=null) {
			count2=list.get(0)[0];
		}
		
		sql					=	"select count(distinct a.plate) from "+jiankong+" a, guanli_kehu_type b where a.plate=b.plate and b.type_id="+TAG_KIND3_CLIENT+" and a.jiankong_id between "+t1+" and "+t2;
		list				=	baseUtil.select(sql);
		
		if(list!=null) {
			count3=list.get(0)[0];
		}
		
		sql					=	"select count(distinct a.plate) from "+jiankong+" a, guanli_kehu_type b where a.plate=b.plate and b.type_id="+TAG_KIND4_CLIENT+" and a.jiankong_id between "+t1+" and "+t2;
		list				=	baseUtil.select(sql);
		
		if(list!=null) {
			count4=list.get(0)[0];
		}

		//	统计总表的数据显示;
		String car_type		=	"kind1:"+count1+"kind2:"+count2+"kind3:"+count3+"kind4:"+count4;
		sql					=	"update "+tongji+" set tongji_kehu_zonggongsi='"+car_type+"' where year="+year+" and month="+month+" and day="+day;
		baseUtil.update(sql);
		
		//	统计分站点的数据集;
		sql					=	"select count(distinct a.plate) from "+jiankong+" a, "+guanli+" b where a.plate=b.plate and b.type_id="+TAG_KIND1_CLIENT+" and a.jiankong_id between "+t1+" and "+t2;
		
		list				=	baseUtil.select(sql);
		
		if(list!=null) {
			count1=list.get(0)[0];
		}
		
		sql					=	"select count(distinct a.plate) from "+jiankong+" a, "+guanli+" b where a.plate=b.plate and b.type_id="+TAG_KIND2_CLIENT+" and a.jiankong_id between "+t1+" and "+t2;
		list				=	baseUtil.select(sql);
		
		if(list!=null) {
			count2=list.get(0)[0];
		}
		
		sql					=	"select count(distinct a.plate) from "+jiankong+" a, "+guanli+" b where a.plate=b.plate and b.type_id="+TAG_KIND3_CLIENT+" and a.jiankong_id between "+t1+" and "+t2;
		list				=	baseUtil.select(sql);
		
		if(list!=null) {
			count3=list.get(0)[0];
		}
		
		sql					=	"select count(distinct a.plate) from "+jiankong+" a, "+guanli+" b where a.plate=b.plate and b.type_id="+TAG_KIND4_CLIENT+" and a.jiankong_id between "+t1+" and "+t2;
		list				=	baseUtil.select(sql);
		
		if(list!=null) {
			count4=list.get(0)[0];
		}
		
		//	统计总表的数据显示;
		car_type			=	"kind1:"+count1+"kind2:"+count2+"kind3:"+count3+"kind4:"+count4;
		sql					=	"update "+tongji+" set tongji_kehu_fenzhandian='"+car_type+"' where year="+year+" and month="+month+" and day="+day;
		baseUtil.update(sql);
	}
}
