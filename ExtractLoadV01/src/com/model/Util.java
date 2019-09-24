package com.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.UUID;

import com.models.Utils;

/**
 * @author Lenovo
 *
 */
/**
 * @author Lenovo
 *
 */
public class Util implements Utils{

	public static final String FOLDER="SYS"+File.separator+"SINOPEC";
	public static final String PATH = "D:"+File.separator+FOLDER+File.separator+"SYS_image";
	public static final String PATH2= "D:"+File.separator+FOLDER+File.separator+"SYS_download";
	public static final String PATH3= "D:"+File.separator+FOLDER+File.separator+"SYS_info";
	
	//	文件存储的标签;
	public String KEY_1="pic";
	public String TAG_1="%";
	public String TAG_2="\\";
	
	//	计时的标签参数;
	public String tag; 
	private long t1;
	private long t2;

	
	//	构造函数的内容;
	public Util() {
		
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	//	重载文件监测的内容;
	public void checkFile(String path) {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}
	//	开始进行程序的相应的操作;
	public void tagStartModule() {
		System.out.println("'"+this.tag+"'功能-开始运行!");
		this.t1=System.currentTimeMillis();
	}
	
	//	结束进行程序的相应的操作;
	public void tagEndModule() {
		System.out.println("--------------------------------");
		System.out.println("'"+this.tag+"'功能-结束运行!");
		this.t2=System.currentTimeMillis();
	}
	
	//	程序花费时间的相应的操作;
	public void tagComputeProcessingTime() {
		long td=this.t2-this.t1;
		System.out.println("'"+this.tag+"'功能-花费时间:"+td+"毫秒!");
		System.out.println("--------------------------------");
	}

	//	获得系统获得UUID的方法;
	public String getUUID() {
		return UUID.randomUUID().toString();
	}
	//	获得相应的时间内容;
	@Override
	public String getCurrentDatetime(long ltime) {
//		long   ltime	   			= System.currentTimeMillis();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		String currenttime 			= dateFormat.format(ltime); 
		return currenttime;
	}
	
}
