package models;

public interface Utils {
	//	获得相应的时间内容;
	abstract String getCurrentDatetime(long ltime,String format);
	
	//	将时间转换为长整型数;
	abstract long transDateStr2Long(String date, String format);
}
