package com.test;


import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.List;

import com.model.Util_DBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) {
		
		String str="{\"total\":5,\"result\":\"true\",\"rows\":[{\"CP_NO\":\"津AX0652\",\"DEPOT_CODE\":\"32600610\",\"NODENO\":\"32600129\",\"OILS_ID\":\"60522972\",\"PSD_ID\":\"0044041427\",\"PS_DATE\":\"2019-08-01 00:00:00\",\"XQL\":5320},{\"CP_NO\":\"津AX0652\",\"DEPOT_CODE\":\"32600610\",\"NODENO\":\"32600129\",\"OILS_ID\":\"60522773\",\"PSD_ID\":\"0044041428\",\"PS_DATE\":\"2019-08-01 00:00:00\",\"XQL\":8360},{\"CP_NO\":\"津AX0652\",\"DEPOT_CODE\":\"92740021\",\"NODENO\":\"32600129\",\"OILS_ID\":\"60522773\",\"PSD_ID\":\"0044057805\",\"PS_DATE\":\"2019-08-01 00:00:00\",\"XQL\":8360},{\"CP_NO\":\"津AX0652\",\"DEPOT_CODE\":\"92740021\",\"NODENO\":\"32600129\",\"OILS_ID\":\"60522773\",\"PSD_ID\":\"0044057806\",\"PS_DATE\":\"2019-08-01 00:00:00\",\"XQL\":5320},{\"CP_NO\":\"津AX0652\",\"DEPOT_CODE\":\"32600610\",\"NODENO\":\"32600129\",\"OILS_ID\":\"60522773\",\"PSD_ID\":\"0044057866\",\"PS_DATE\":\"2019-08-01 00:00:00\",\"XQL\":5320}]}";
		
		JSONObject  object=JSONObject.fromObject(str);
		String 		rows=object.getString("rows");
		
		JSONArray   array=JSONArray.fromObject(rows);
		
		for(int i=0;i<array.size();i++) {
			JSONObject o=array.getJSONObject(i);
//			String CKD_ID=o.getString("CKD_ID");
//			String CP_NO =o.getString("CP_NO");
//			String DEPOT_CODE=o.getString("DEPOT_CODE");
//			String DONE_FLAG=o.getString("DONE_FLAG");
//			String FH_BULK=o.getString("FH_BULK");
//			String FH_DATE=o.getString("FH_DATE");
//			String FH_WEIGHT=o.getString("FH_WEIGHT");
//			String OILS_ID=o.getString("OILS_ID");
//			String PSD_ID=o.getString("PSD_ID");
//			String PS_TANK=o.getString("PS_TANK");
//			String nodeno=o.getString("nodeno");
			System.out.println(o.toString());
		}
		
		
		
		
		
//		Util_DBase base=new Util_DBase();
//		
//		boolean flag=base.checkDate("2019-1", "yyyy");
//		System.out.println(flag);
//		System.out.println("Hello World");
		
//		Service service=new Service(provider, type, algorithm, className, aliases, attributes);
		
		
		
	}
	
	
//	public List<HourTankData> getHourTankData(String Nodeno,String Oilcan){
//		List<HourTankData> lst    		= new ArrayList<HourTankData>();
//		
//		try {
//			ChecktankLocator   service		= new ChecktankLocator();
//			ChecktankPortType  serviceSoap	= service.getchecktankHttpSoap11Endpoint();
//			String 			   stringJson	= serviceSoap.CIF_OR_ID004(Nodeno,Oilcan);
//			JSONObject 		   jsonObject	= new JSONObject(stringJson);
//			
//			System.out.println("json="+jsonObject.toString());
////			if(jsonObject.getInt("total")==0) {
////				System.out.println("aaaabbbccd");
////				return lst;
////			}
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		
//		return lst;
//	}
	
}
