package com.free.dc.tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Utils {
	//	监测文件夹是否存在的方法;
	abstract void checkFile(String path);
	//	功能开始的标签内容;
	abstract void tagStartModule();
	//	功能结束的标签内容;
	abstract void tagEndStartModule();
	//	计算程序时间长内容;
	abstract void tagComputeProcessingTime();
	//	网络配置设置;
	abstract void setHttpServletParameter(HttpServletRequest request, HttpServletResponse response);
	//	获得UUID的方法;
	abstract String getUUID();
	//	获得当前的时间;
	abstract String getNewTime();
}
