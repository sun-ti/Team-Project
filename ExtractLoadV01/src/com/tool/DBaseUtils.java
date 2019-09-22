package com.tool;

import java.util.ArrayList;

import net.sf.json.JSONArray;

//	进行数据库操作的接口;
public interface DBaseUtils {

	abstract ArrayList<String[]> select(String sql);
	
	abstract JSONArray select2(Util util, String sql);
	
	abstract int getQueryCount(String sql);
}
