package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.Utils;

public class Util implements Utils{

	public Util() {
		
	}
	
	//	获取当前天的初试时间,结束时间;
	public static long[] getCurrentTimeStrs(long current) {
		//	进行时间的声明;
		long[]  times= new long[2];
		Util 	util = new Util();
		
		String 	date = util.getCurrentDatetime(current, "yyyy-MM-dd");
		
		String  start= date+" 00:00:00";
		
		String  end  = date+" 23:59:59";
		
		//	初始时间;
		times[0]	 = util.transDateStr2Long(start, "yyyy-MM-dd HH:mm:ss");
		
		//	结束时间;
		times[1]	 = util.transDateStr2Long(end, "yyyy-MM-dd HH:mm:ss");
		
		//	返回对象;		
		return times;
	}
	
	//	获得相应的时间内容;
	public String getCurrentDatetime(long ltime,String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);//可以方便地修改日期格式
		String currenttime 			= dateFormat.format(ltime); 
		return currenttime;
	}
	
	//	将时间转换为长整型数;
	public long transDateStr2Long(String date, String format) {
		long 		result	 = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		//	结果信息内容;
		try {
			result			 = sdf.parse(date).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	//	进行数据的过滤;
	public String checkInputCode(String str) {
		
		//	判断参数中是否有相同的内容变量;
		if(str.contains("\"{")) {
			str=str.replace("\"{", "{");
		}
		//	对数据信息进行过滤;
		if(str.contains("}\"")) {
			str=str.replace("}\"", "}");
		}
		return str;
	}
}
