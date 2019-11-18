package compute;

import java.util.ArrayList;
import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.DBaseUtil;
import model.Util;


public class TJ_GetDataFromYWY extends Util{

	//	数据库的相应内容;
	private DBaseUtil baseUtil;
	
	public TJ_GetDataFromYWY(DBaseUtil baseUtil) {
		super();
		this.baseUtil=baseUtil;
	}
	
	public void compute() {
		
		String  			endpoint 		= "http://10.178.1.160:8080/services/checktank?wsdl";
		String  			targetnamespace = "http://webservice.checktank.sh";
		String  			charset			= "UTF-8";
		// 参数:nodeno,outDate
		String 				temp			= "";
		//	网络数据的返回值;
		String  			resultWeb		= null;
		Call 				call			= null;
		//	数据对象;
		JsonObject 			jsonObj			= null;
		//	数据列表;
		JsonArray   		jsonArray		= null;
		//	服务端的程序;
		Service 			service			= new Service();

		//	sql语句;
		String 				sql				= "";
		//	获取结果的容器;
		ArrayList<String[]> list			= null;

		String  			date			= super.getCurrentDate();
		//	时间超时的设置;
		int				    second			= 60;
		int 				timeout			= second*1000;
		
		try {
			// 进行service数据的交互确认;
			call				= (Call) service.createCall();
			
			// 进行相应的末点描述;
			call.setTargetEndpointAddress(endpoint);
			
			// 方法WDSL中的方法描述;
			call.setOperationName(new QName(targetnamespace, "CIF_OR_ID005"));
			
			// 设置webservice的参数;
			call.addParameter("nodeno", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
			call.addParameter("deliveryDate", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
			call.setEncodingStyle(charset);
			//	设置时间超时;
			call.setTimeout(timeout);
			
			// 设置返回值的类型内容;
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			
			//	站点编号-"00000000"是对全站点进行统计;
			String  stationid	= "00000000";
			//	进行选择-配送单的表名称;
			String 	peisongdan	= "guanli_peisongdan";
			
			//	呼叫网络的数据内容;
			resultWeb			= (String)call.invoke(new Object[]{stationid,date}).toString().trim();
			
			//	对结果进行数据处理;
			//	当数据结果不为空并且不是空字符串的情况下;
			if(resultWeb!=null&&!resultWeb.equals("")) {
				
				//	检测当前的配送单表单是否存在;
				checkTableExists();
				
				//	对结果集合进行数据的抽取以及插入;
				jsonObj				= new JsonParser().parse(resultWeb).getAsJsonObject();
				
				temp				= jsonObj.get("rows").toString();
				
				//	结果对象不为空的情况下;
				if(jsonObj!=null) {
					//	将数据结果形成列表;
					jsonArray		= new JsonParser().parse(temp).getAsJsonArray();
					
					//	将数据集合进行加载;
					for(int i=0;i<jsonArray.size();i++) {
						JsonObject  obj			 =	jsonArray.get(i).getAsJsonObject();
						long 		peisongdan_id=	System.currentTimeMillis();	
						
						String 		nodeno 		 = 	obj.get("NODENO").toString().trim();
						if(nodeno.contains("\"")) {
							nodeno=nodeno.replace("\"", "");
						}
						
						String 		xql			 =	obj.get("XQL").toString().trim();
						if(xql.contains("\"")) {
							xql=xql.replace("\"", "");
						}
						
						//	配送日期;
						String 		ps_date 	 =   obj.get("PS_DATE").toString().trim();
						if(ps_date.contains("\"")) {
							ps_date=ps_date.replace("\"", "");
						}
						
						//	配送车牌号;
						String 		cp_no		 =	obj.get("CP_NO").toString().trim();
						if(cp_no.contains("\"")) {
							cp_no=cp_no.replace("\"", "");
						}
						
						//	油库代码;
						String 		depot_code	 =	obj.get("DEPOT_CODE").toString().trim();
						if(depot_code.contains("\"")) {
							depot_code=depot_code.replace("\"", "");
						}
						
						//	配送单id;
						String 		psd_id		 =	obj.get("PSD_ID").toString().trim();
						if(psd_id.contains("\"")) {
							psd_id=psd_id.replace("\"", "");
						}
						
						//	油品id;
						String 		oils_id		 =	obj.get("OILS_ID").toString().trim();
						if(oils_id.contains("\"")) {
							oils_id=oils_id.replace("\"", "");
						}
						
						String 	    state		 = "0";
						
						//	配送车辆信息的检索;
						checkXieyoucheExists(cp_no);
						
						//	检查是否有相同的内容的数据信息;
						sql		=	"select count(*) from "+peisongdan+" where psd_id='"+psd_id+"' limit 0,1";
						//	SQL语句的结果查询;
						list	=	baseUtil.select(sql);
						
						if(list!=null) {
							//	临时参量;
							temp		=	list.get(0)[0];
							
							int count	=	Integer.parseInt(temp);
							
							//	当存在的情况下;
							if(count!=0) {
								
								//	进行数据的更新;
								sql="update "+peisongdan+" set xql='"+xql+"',ps_date='"+ps_date+"',cp_no='"+cp_no+"',depot_code='"+depot_code+"',oils_id='"+oils_id+"' where psd_id='"+psd_id+"'";
							}
							//	当不存在情况下;
							else {
								
								//	进行数据的插入;
								sql="insert into "+peisongdan+" (peisongdan_id,nodeno,xql,ps_date,cp_no,depot_code,psd_id,oils_id,state) values ("+ 
									""+peisongdan_id+",'"+nodeno+"','"+xql+"','"+ps_date+"','"+cp_no+"','"+depot_code+"','"+psd_id+"','"+oils_id+"',"+state+")";
							}
							baseUtil.update(sql);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//	检测卸油车是否存在;
	private void checkXieyoucheExists(String cp_no) {
		
		//	进行卸油车校验;
		String 				sql		=	"select count(distinct plate) from guanli_xieyouche where plate='"+cp_no+"'";
		ArrayList<String[]> list	=	baseUtil.select(sql);
		//	临时变量;
		String 				temp	=	"";
		
		if(list.size()!=0) {
			
			temp		=	list.get(0)[0];
			
			//	数据结果的数值;
			int count	=	Integer.parseInt(temp);
			
			//	当不能查询出相应的数据时;
			if(count==0) {
				
				//	新建卸油车的数据信息;
				//	卸油车的id编号;
				long 	xieyouche_id=	System.currentTimeMillis();
				//	车牌编号;
				String 	plate		=	cp_no;
				String  state		=	"0";
				//	新增数据SQL语句;
				sql					=	"insert into guanli_xieyouche (xieyouche_id,plate,state) values ("+xieyouche_id+",'"+plate+"',"+state+")";
				//	数据更新;
				baseUtil.update(sql);
			}
		}
	}
	
	
	
	
	//	进行表的检测;
	private void checkTableExists() {
		String 				dbname	   = baseUtil.getDb_name();
		String 				peisongdan = "guanli_peisongdan";
		String              sql        = "";
		ArrayList<String[]> list       = null;
		
		//	检测统计表是否存在;
        sql							   = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+dbname+"' and TABLE_NAME='"+peisongdan+"'";
        list						   = baseUtil.select(sql);
        
        //  当表单不存在的情况下;
        if(list.size()==0){
            //  进行表单的创建;
            sql="create table "+peisongdan+" (" +
                "autoid bigint primary key auto_increment," +
                "peisongdan_id bigint," +
                "nodeno varchar(50)," +
                "xql varchar(50)," +
                "ps_date varchar(50),"+ 
                "cp_no varchar(50),"+ 
                "depot_code varchar(50),"+ 
                "psd_id varchar(50),"+
                "oils_id varchar(50)," +
                "state int"+ 
                ")";
            //  进行更新的操作;
            baseUtil.update(sql);
        }
        
        //	检测卸油车辆;
        String 		xieyouche	= "guanli_xieyouche";
        sql						= "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+dbname+"' and TABLE_NAME='"+xieyouche+"'";
        list					= baseUtil.select(sql);
        
        //	当查询结果为0时;
        if(list.size()==0) {
        	//	进行表单的创建;
        	sql=
			"create table "+xieyouche+" ("+ 
        	"autoid bigint primary key auto_increment,"+ 
			"xieyouche_id bigint,"+ 
        	"plate varchar(30),"+ 
			"note varchar(500),"+ 
        	"state int)";
        	//	进行更新的操作;
        	baseUtil.update(sql);
        }
        
        
	}
}
