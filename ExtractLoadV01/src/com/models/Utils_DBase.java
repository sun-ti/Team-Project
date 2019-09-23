package com.models;

import net.sf.json.JSONArray;

//	进行数据库操作的接口;
public interface Utils_DBase {
	//	数据的查询;
	abstract JSONArray select(String sql);
	//	数据的总数;
	abstract int getQueryCount(String sql);
	//	数据的更新;
	abstract int update(String sql);
}
