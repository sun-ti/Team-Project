package com.models;

public interface Utils {
	//	监测文件夹是否存在的方法;
	abstract void checkFile(String path);
	//	功能开始的标签内容;
	abstract void tagStartModule();
	//	功能结束的标签内容;
	abstract void tagEndModule();
	//	计算程序时间长内容;
	abstract void tagComputeProcessingTime();

	//	获得UUID的方法;
	abstract String getUUID();
	//	获得当前的时间;
	abstract String getCurrentDatetime(long ltime,String format);
	//	进行项目的设置;
	abstract long transDateStr2Long(String date,String format);
	
//	abstract void getHttpUrl();
}
